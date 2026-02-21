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

/**
 * Result object for PKCE authorization start.
 *
 * Contains the Spotify Accounts authorization URL and the generated `state` value.
 */
data class PkceAuthorizationRequest(
    val authorizationUri: String,
    val state: String,
)

private data class PendingPkce(
    val codeVerifier: String,
    val state: String,
)

/**
 * High-level authorization manager for Spotify Accounts flows.
 *
 * Handles PKCE state/verifier lifecycle, code exchange, token refresh, and token caching.
 */
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

    /**
     * Starts Authorization Code with PKCE flow and returns authorization request data.
     *
     * @param scope Spotify OAuth scopes requested for this flow.
     * @param showDialog Whether to force Spotify consent dialog display.
     * @return PKCE authorization request containing the authorization URL and `state`.
     */
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

    /**
     * Starts Authorization Code with PKCE flow and launches the authorization URL.
     *
     * @param scope Spotify OAuth scopes requested for this flow.
     * @param showDialog Whether to force Spotify consent dialog display.
     * @return PKCE authorization request containing the authorization URL and `state`.
     */
    @OptIn(ExperimentalEncodingApi::class)
    fun startPkceAuthorizationAndLaunch(
        scope: List<String> = emptyList(),
        showDialog: Boolean? = null,
    ): PkceAuthorizationRequest {
        val request =
            startPkceAuthorization(
                scope = scope,
                showDialog = showDialog,
            )
        launchAuthorizationInAppOrBrowser(request.authorizationUri)
        return request
    }

    /**
     * Starts Authorization Code with PKCE flow asynchronously.
     *
     * @param scope Spotify OAuth scopes requested for this flow.
     * @param showDialog Whether to force Spotify consent dialog display.
     * @return PKCE authorization request containing the authorization URL and `state`.
     */
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

    /**
     * Starts Authorization Code with PKCE flow asynchronously and launches the authorization URL.
     *
     * @param scope Spotify OAuth scopes requested for this flow.
     * @param showDialog Whether to force Spotify consent dialog display.
     * @return PKCE authorization request containing the authorization URL and `state`.
     */
    @OptIn(ExperimentalEncodingApi::class)
    suspend fun startPkceAuthorizationAsyncAndLaunch(
        scope: List<String> = emptyList(),
        showDialog: Boolean? = null,
    ): PkceAuthorizationRequest {
        val request =
            startPkceAuthorizationAsync(
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

        pendingPkce =
            PendingPkce(
                codeVerifier = verifier,
                state = state,
            )

        val authorizationUri =
            authorizationApis.buildAuthorizationCodeWithPkceUri(
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

    /**
     * Completes PKCE authorization by parsing the Spotify callback URI and exchanging the code.
     *
     * @param redirectedUri Callback URI returned by Spotify after user authorization.
     * @return Token response returned by Spotify Accounts service.
     */
    suspend fun completePkceAuthorizationFromRedirectUri(redirectedUri: String): TokenResponse =
        mutex.withLock {
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

    /**
     * Completes PKCE authorization by exchanging the authorization code.
     *
     * @param code Authorization code issued by Spotify Accounts service.
     * @param returnedState State value returned by Spotify callback URI.
     * @return Token response returned by Spotify Accounts service.
     */
    suspend fun completePkceAuthorization(
        code: String,
        returnedState: String,
    ): TokenResponse =
        mutex.withLock {
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
        val token =
            authorizationApis
                .requestAuthorizationCodeWithPkceToken(
                    clientId = clientId,
                    code = code,
                    redirectUri = requireRedirectUri(),
                    codeVerifier = pending.codeVerifier,
                ).requireSuccessToken("Failed to complete PKCE authorization.")
        pendingPkce = null
        return installToken(token)
    }

    /**
     * Builds the Spotify authorization URL for Authorization Code with PKCE flow.
     *
     * @param codeChallenge PKCE code challenge derived from the code verifier.
     * @param codeChallengeMethod PKCE challenge method, typically `S256`.
     * @param scope Spotify OAuth scopes requested for this flow.
     * @param state Opaque state value used for CSRF protection during authorization.
     * @param showDialog Whether to force Spotify consent dialog display.
     * @return Spotify authorization URL.
     */
    fun buildAuthorizationCodeWithPkceUri(
        codeChallenge: String,
        codeChallengeMethod: String = "S256",
        scope: List<String> = emptyList(),
        state: String? = null,
        showDialog: Boolean? = null,
    ): String =
        authorizationApis.buildAuthorizationCodeWithPkceUri(
            clientId = clientId,
            redirectUri = requireRedirectUri(),
            codeChallenge = codeChallenge,
            codeChallengeMethod = codeChallengeMethod,
            scope = scope,
            state = state,
            showDialog = showDialog,
        )

    /**
     * Builds and launches the Spotify authorization URL for PKCE flow.
     *
     * @param codeChallenge PKCE code challenge derived from the code verifier.
     * @param codeChallengeMethod PKCE challenge method, typically `S256`.
     * @param scope Spotify OAuth scopes requested for this flow.
     * @param state Opaque state value used for CSRF protection during authorization.
     * @param showDialog Whether to force Spotify consent dialog display.
     * @return Spotify authorization URL.
     */
    fun buildAuthorizationCodeWithPkceUriAndLaunch(
        codeChallenge: String,
        codeChallengeMethod: String = "S256",
        scope: List<String> = emptyList(),
        state: String? = null,
        showDialog: Boolean? = null,
    ): String {
        val authorizationUri =
            buildAuthorizationCodeWithPkceUri(
                codeChallenge = codeChallenge,
                codeChallengeMethod = codeChallengeMethod,
                scope = scope,
                state = state,
                showDialog = showDialog,
            )
        launchAuthorizationInAppOrBrowser(authorizationUri)
        return authorizationUri
    }

    /**
     * Builds the Spotify authorization URL for Authorization Code flow.
     *
     * @param scope Spotify OAuth scopes requested for this flow.
     * @param state Opaque state value used for CSRF protection during authorization.
     * @param showDialog Whether to force Spotify consent dialog display.
     * @return Spotify authorization URL.
     */
    fun buildAuthorizationCodeUri(
        scope: List<String> = emptyList(),
        state: String? = null,
        showDialog: Boolean? = null,
    ): String =
        authorizationApis.buildAuthorizationCodeUri(
            clientId = clientId,
            redirectUri = requireRedirectUri(),
            scope = scope,
            state = state,
            showDialog = showDialog,
        )

    /**
     * Builds and launches the Spotify authorization URL for Authorization Code flow.
     *
     * @param scope Spotify OAuth scopes requested for this flow.
     * @param state Opaque state value used for CSRF protection during authorization.
     * @param showDialog Whether to force Spotify consent dialog display.
     * @return Spotify authorization URL.
     */
    fun buildAuthorizationCodeUriAndLaunch(
        scope: List<String> = emptyList(),
        state: String? = null,
        showDialog: Boolean? = null,
    ): String {
        val authorizationUri =
            buildAuthorizationCodeUri(
                scope = scope,
                state = state,
                showDialog = showDialog,
            )
        launchAuthorizationInAppOrBrowser(authorizationUri)
        return authorizationUri
    }

    /**
     * Launches the Spotify authorization URL using in-app auth UI or browser.
     *
     * @param authorizationUri Spotify Accounts authorization URL to open.
     * @return True when the operation succeeds.
     */
    fun launchAuthorizationInAppOrBrowser(authorizationUri: String): Boolean =
        runCatching { authorizationUriLauncher(authorizationUri) }
            .getOrElse { false }

    /**
     * Exchanges an authorization code for tokens using PKCE and stores the token set.
     *
     * @param code Authorization code issued by Spotify Accounts service.
     * @param codeVerifier PKCE code verifier used when exchanging authorization code.
     * @return Token response returned by Spotify Accounts service.
     */
    suspend fun exchangeAuthorizationCodeWithPkce(
        code: String,
        codeVerifier: String,
    ): TokenResponse =
        mutex.withLock {
            val token =
                authorizationApis
                    .requestAuthorizationCodeWithPkceToken(
                        clientId = clientId,
                        code = code,
                        redirectUri = requireRedirectUri(),
                        codeVerifier = codeVerifier,
                    ).requireSuccessToken("Failed to exchange authorization code with PKCE.")
            installToken(token)
        }

    /**
     * Exchanges an authorization code for tokens and stores the token set.
     *
     * @param code Authorization code issued by Spotify Accounts service.
     * @return Token response returned by Spotify Accounts service.
     */
    suspend fun exchangeAuthorizationCode(code: String): TokenResponse =
        mutex.withLock {
            val token =
                authorizationApis
                    .requestAuthorizationCodeToken(
                        clientId = clientId,
                        clientSecret = requireClientSecret(),
                        code = code,
                        redirectUri = requireRedirectUri(),
                    ).requireSuccessToken("Failed to exchange authorization code.")
            installToken(token)
        }

    /**
     * Requests a client-credentials token and stores the token set.
     *
     * @return Token response returned by Spotify Accounts service.
     */
    suspend fun requestClientCredentialsToken(): TokenResponse =
        mutex.withLock {
            val token =
                authorizationApis
                    .requestClientCredentialsToken(
                        clientId = clientId,
                        clientSecret = requireClientSecret(),
                    ).requireSuccessToken("Failed to request client credentials token.")
            installToken(token)
        }

    /**
     * Refreshes the current access token using the stored refresh token.
     *
     * @return Token response returned by Spotify Accounts service.
     */
    suspend fun refreshAccessToken(): TokenResponse =
        mutex.withLock {
            val current = tokenResponse ?: error("Token is missing. Acquire token first.")
            val refreshToken = current.refreshToken ?: error("Refresh token is missing for this flow.")

            val refreshed =
                if (clientSecret != null) {
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
                    refreshToken = refreshed.refreshToken ?: current.refreshToken,
                ),
            )
        }

    /**
     * Returns a valid access token, refreshing it when necessary.
     *
     * @param leewaySeconds Safety window in seconds before actual expiration when the token is treated as expired.
     * @param autoRefresh Whether to automatically execute Refresh Token flow when the current token is missing or expired.
     * @return Bearer access token to use in Spotify Web API requests.
     */
    suspend fun getValidAccessToken(
        leewaySeconds: Int = 60,
        autoRefresh: Boolean = true,
    ): String =
        mutex.withLock {
            val current = tokenResponse
            if (current != null && isTokenValid(leewaySeconds)) {
                return@withLock current.accessToken
            }

            if (!autoRefresh) {
                error("Token is missing or expired. Set autoRefresh=true or fetch a token first.")
            }

            val refreshToken = current?.refreshToken ?: error("Refresh token is missing for this flow.")
            val refreshed =
                if (clientSecret != null) {
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
                    refreshToken = refreshed.refreshToken ?: current.refreshToken,
                ),
            )
            refreshed.accessToken
        }

    /**
     * Returns the currently stored token response.
     *
     * @return Currently cached token response, or `null` if no token is stored.
     */
    fun getCurrentToken(): TokenResponse? = tokenResponse

    /**
     * Clears the currently stored token and expiration state.
     *
     * @return No return value.
     */
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

    private fun requireClientSecret(): String = clientSecret ?: error("clientSecret is required for this flow.")

    private fun requireRedirectUri(): String = redirectUri ?: error("redirectUri is required for this flow.")

    private fun SpotifyApiResponse<TokenResponse>.requireSuccessToken(context: String): TokenResponse =
        when (val body = data) {
            is SpotifyResponseData.Success -> body.value
            is SpotifyResponseData.Error ->
                error(
                    "$context (status=${body.value.error.status}): ${body.value.error.message}",
                )
        }

    @OptIn(ExperimentalEncodingApi::class)
    private fun generateCodeVerifier(): String = toBase64Url(secureRandomBytes(64))

    @OptIn(ExperimentalEncodingApi::class)
    private fun generateState(): String = toBase64Url(secureRandomBytes(16))

    @OptIn(ExperimentalEncodingApi::class)
    private fun toBase64Url(bytes: ByteArray): String =
        Base64
            .encode(bytes)
            .replace("+", "-")
            .replace("/", "_")
            .trimEnd('=')
}
