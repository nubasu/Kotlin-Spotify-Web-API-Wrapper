package com.nubasu.spotify.webapi.wrapper.api.authorization

import com.nubasu.spotify.webapi.wrapper.response.authorization.TokenResponse
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyResponseData
import com.nubasu.spotify.webapi.wrapper.utils.Sha256
import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder
import com.nubasu.spotify.webapi.wrapper.utils.secureRandomBytes
import io.ktor.http.Url
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.time.Clock

data class PkceAuthorizationRequest(
    val authorizationUri: String,
    val state: String,
)

private data class PendingPkce(
    val codeVerifier: String,
    val state: String,
)

class SpotifyAuthManager(
    private val clientId: String,
    private val clientSecret: String? = null,
    private val redirectUri: String? = null,
    private val authorizationApis: AuthorizationApis = AuthorizationApis(),
    private val authorizationUriLauncher: (String) -> Boolean = ::launchAuthorizationUriOnPlatform,
) {
    private val mutex = Mutex()
    private var tokenResponse: TokenResponse? = null
    private var accessTokenExpiresAtMs: Long? = null
    private var pendingPkce: PendingPkce? = null

    @OptIn(ExperimentalEncodingApi::class)
    fun startPkceAuthorization(
        scope: List<String> = emptyList(),
        showDialog: Boolean? = null,
    ): PkceAuthorizationRequest {
        val verifier = generateCodeVerifier()
        val challenge = toBase64Url(Sha256.digest(verifier.encodeToByteArray()))
        return buildPkceAuthorizationRequest(
            verifier = verifier,
            challenge = challenge,
            scope = scope,
            showDialog = showDialog,
        )
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun startPkceAuthorizationAndLaunch(
        scope: List<String> = emptyList(),
        showDialog: Boolean? = null,
    ): PkceAuthorizationRequest {
        val request = startPkceAuthorization(
            scope = scope,
            showDialog = showDialog,
        )
        launchAuthorizationInAppOrBrowser(request.authorizationUri)
        return request
    }

    @OptIn(ExperimentalEncodingApi::class)
    suspend fun startPkceAuthorizationAsync(
        scope: List<String> = emptyList(),
        showDialog: Boolean? = null,
    ): PkceAuthorizationRequest {
        val verifier = generateCodeVerifier()
        val challenge = toBase64Url(Sha256.digest(verifier.encodeToByteArray()))
        return buildPkceAuthorizationRequest(
            verifier = verifier,
            challenge = challenge,
            scope = scope,
            showDialog = showDialog,
        )
    }

    @OptIn(ExperimentalEncodingApi::class)
    suspend fun startPkceAuthorizationAsyncAndLaunch(
        scope: List<String> = emptyList(),
        showDialog: Boolean? = null,
    ): PkceAuthorizationRequest {
        val request = startPkceAuthorizationAsync(
            scope = scope,
            showDialog = showDialog,
        )
        launchAuthorizationInAppOrBrowser(request.authorizationUri)
        return request
    }

    private fun buildPkceAuthorizationRequest(
        verifier: String,
        challenge: String,
        scope: List<String>,
        showDialog: Boolean?,
    ): PkceAuthorizationRequest {
        val state = generateState()

        pendingPkce = PendingPkce(
            codeVerifier = verifier,
            state = state,
        )

        val authorizationUri = authorizationApis.buildAuthorizationCodeWithPkceUri(
            clientId = clientId,
            redirectUri = requireRedirectUri(),
            codeChallenge = challenge,
            codeChallengeMethod = "S256",
            scope = scope,
            state = state,
            showDialog = showDialog,
        )

        return PkceAuthorizationRequest(
            authorizationUri = authorizationUri,
            state = state,
        )
    }

    suspend fun completePkceAuthorizationFromRedirectUri(
        redirectedUri: String,
    ): TokenResponse = mutex.withLock {
        val url = Url(redirectedUri)

        val error = url.parameters["error"]
        if (error != null) {
            val description = url.parameters["error_description"]
            throw RuntimeException("Spotify authorization failed: $error${description?.let { " - $it" } ?: ""}")
        }

        val code = url.parameters["code"] ?: error("Authorization code is missing in callback URI.")
        val returnedState = url.parameters["state"] ?: error("State is missing in callback URI.")
        completePkceAuthorizationInternal(
            code = code,
            returnedState = returnedState,
        )
    }

    suspend fun completePkceAuthorization(
        code: String,
        returnedState: String,
    ): TokenResponse = mutex.withLock {
        completePkceAuthorizationInternal(
            code = code,
            returnedState = returnedState,
        )
    }

    private suspend fun completePkceAuthorizationInternal(
        code: String,
        returnedState: String,
    ): TokenResponse {
        val pending = pendingPkce ?: error("PKCE session is missing. Call startPkceAuthorization() first.")
        if (returnedState != pending.state) {
            throw IllegalStateException("State mismatch in callback.")
        }
        val token = authorizationApis.requestAuthorizationCodeWithPkceToken(
            clientId = clientId,
            code = code,
            redirectUri = requireRedirectUri(),
            codeVerifier = pending.codeVerifier,
        ).requireSuccessToken("Failed to complete PKCE authorization.")
        pendingPkce = null
        return installToken(token)
    }

    fun buildAuthorizationCodeWithPkceUri(
        codeChallenge: String,
        codeChallengeMethod: String = "S256",
        scope: List<String> = emptyList(),
        state: String? = null,
        showDialog: Boolean? = null,
    ): String {
        return authorizationApis.buildAuthorizationCodeWithPkceUri(
            clientId = clientId,
            redirectUri = requireRedirectUri(),
            codeChallenge = codeChallenge,
            codeChallengeMethod = codeChallengeMethod,
            scope = scope,
            state = state,
            showDialog = showDialog,
        )
    }

    fun buildAuthorizationCodeWithPkceUriAndLaunch(
        codeChallenge: String,
        codeChallengeMethod: String = "S256",
        scope: List<String> = emptyList(),
        state: String? = null,
        showDialog: Boolean? = null,
    ): String {
        val authorizationUri = buildAuthorizationCodeWithPkceUri(
            codeChallenge = codeChallenge,
            codeChallengeMethod = codeChallengeMethod,
            scope = scope,
            state = state,
            showDialog = showDialog,
        )
        launchAuthorizationInAppOrBrowser(authorizationUri)
        return authorizationUri
    }

    fun buildAuthorizationCodeUri(
        scope: List<String> = emptyList(),
        state: String? = null,
        showDialog: Boolean? = null,
    ): String {
        return authorizationApis.buildAuthorizationCodeUri(
            clientId = clientId,
            redirectUri = requireRedirectUri(),
            scope = scope,
            state = state,
            showDialog = showDialog,
        )
    }

    fun buildAuthorizationCodeUriAndLaunch(
        scope: List<String> = emptyList(),
        state: String? = null,
        showDialog: Boolean? = null,
    ): String {
        val authorizationUri = buildAuthorizationCodeUri(
            scope = scope,
            state = state,
            showDialog = showDialog,
        )
        launchAuthorizationInAppOrBrowser(authorizationUri)
        return authorizationUri
    }

    fun launchAuthorizationInAppOrBrowser(authorizationUri: String): Boolean {
        return runCatching { authorizationUriLauncher(authorizationUri) }
            .getOrElse { false }
    }

    suspend fun exchangeAuthorizationCodeWithPkce(
        code: String,
        codeVerifier: String,
    ): TokenResponse = mutex.withLock {
        val token = authorizationApis.requestAuthorizationCodeWithPkceToken(
            clientId = clientId,
            code = code,
            redirectUri = requireRedirectUri(),
            codeVerifier = codeVerifier,
        ).requireSuccessToken("Failed to exchange authorization code with PKCE.")
        installToken(token)
    }

    suspend fun exchangeAuthorizationCode(
        code: String,
    ): TokenResponse = mutex.withLock {
        val token = authorizationApis.requestAuthorizationCodeToken(
            clientId = clientId,
            clientSecret = requireClientSecret(),
            code = code,
            redirectUri = requireRedirectUri(),
        ).requireSuccessToken("Failed to exchange authorization code.")
        installToken(token)
    }

    suspend fun requestClientCredentialsToken(): TokenResponse = mutex.withLock {
        val token = authorizationApis.requestClientCredentialsToken(
            clientId = clientId,
            clientSecret = requireClientSecret(),
        ).requireSuccessToken("Failed to request client credentials token.")
        installToken(token)
    }

    suspend fun refreshAccessToken(): TokenResponse = mutex.withLock {
        val current = tokenResponse ?: error("Token is missing. Acquire token first.")
        val refreshToken = current.refreshToken ?: error("Refresh token is missing for this flow.")

        val refreshed = if (clientSecret != null) {
            authorizationApis.refreshToken(
                clientId = clientId,
                clientSecret = clientSecret,
                refreshToken = refreshToken,
            )
        } else {
            authorizationApis.refreshTokenWithPkce(
                clientId = clientId,
                refreshToken = refreshToken,
            )
        }.requireSuccessToken("Failed to refresh access token.")
        installToken(
            refreshed.copy(
                refreshToken = refreshed.refreshToken ?: current.refreshToken
            )
        )
    }

    suspend fun getValidAccessToken(
        leewaySeconds: Int = 60,
        autoRefresh: Boolean = true,
    ): String = mutex.withLock {
        val current = tokenResponse
        if (current != null && isTokenValid(leewaySeconds)) {
            return@withLock current.accessToken
        }

        if (!autoRefresh) {
            error("Token is missing or expired. Set autoRefresh=true or fetch a token first.")
        }

        val refreshToken = current?.refreshToken ?: error("Refresh token is missing for this flow.")
        val refreshed = if (clientSecret != null) {
            authorizationApis.refreshToken(
                clientId = clientId,
                clientSecret = clientSecret,
                refreshToken = refreshToken,
            )
        } else {
            authorizationApis.refreshTokenWithPkce(
                clientId = clientId,
                refreshToken = refreshToken,
            )
        }.requireSuccessToken("Failed to refresh access token.")
        installToken(
            refreshed.copy(
                refreshToken = refreshed.refreshToken ?: current.refreshToken
            )
        )
        refreshed.accessToken
    }

    fun getCurrentToken(): TokenResponse? = tokenResponse

    fun clearToken() {
        tokenResponse = null
        accessTokenExpiresAtMs = null
        pendingPkce = null
        TokenHolder.token = ""
    }

    private fun installToken(token: TokenResponse): TokenResponse {
        tokenResponse = token
        accessTokenExpiresAtMs = Clock.System.now().toEpochMilliseconds() + (token.expiresIn * 1000L)
        TokenHolder.token = token.accessToken
        return token
    }

    private fun isTokenValid(leewaySeconds: Int): Boolean {
        val expiresAtMs = accessTokenExpiresAtMs ?: return false
        val nowWithLeeway = Clock.System.now().toEpochMilliseconds() + (leewaySeconds * 1000L)
        return nowWithLeeway < expiresAtMs
    }

    private fun requireClientSecret(): String {
        return clientSecret ?: error("clientSecret is required for this flow.")
    }

    private fun requireRedirectUri(): String {
        return redirectUri ?: error("redirectUri is required for this flow.")
    }

    private fun SpotifyApiResponse<TokenResponse>.requireSuccessToken(context: String): TokenResponse {
        return when (val body = data) {
            is SpotifyResponseData.Success -> body.value
            is SpotifyResponseData.Error -> error(
                "$context (status=${body.value.error.status}): ${body.value.error.message}"
            )
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun generateCodeVerifier(): String {
        return toBase64Url(secureRandomBytes(64))
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun generateState(): String {
        return toBase64Url(secureRandomBytes(16))
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun toBase64Url(bytes: ByteArray): String {
        return Base64.encode(bytes)
            .replace("+", "-")
            .replace("/", "_")
            .trimEnd('=')
    }
}
