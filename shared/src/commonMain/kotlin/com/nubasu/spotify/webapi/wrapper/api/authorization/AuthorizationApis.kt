package com.nubasu.spotify.webapi.wrapper.api.authorization

import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.authorization.TokenResponse
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLBuilder
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * Spotify Accounts authorization API.
 *
 * Provides Authorization Code, PKCE, Client Credentials, and Refresh Token requests.
 */
class AuthorizationApis(
    private val client: HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        },
    private val authorizeEndpoint: String = "https://accounts.spotify.com/authorize",
    private val tokenEndpoint: String = "https://accounts.spotify.com/api/token",
) {
    /**
     * Builds the Spotify Accounts authorization URL for Authorization Code with PKCE flow.
     *
     * @param clientId Spotify application Client ID.
     * @param redirectUri Redirect URI registered in Spotify Dashboard.
     * @param codeChallenge PKCE code challenge derived from the code verifier.
     * @param codeChallengeMethod PKCE challenge method, typically `S256`.
     * @param scope Spotify OAuth scopes requested for this flow.
     * @param state Opaque state value used for CSRF protection during authorization.
     * @param showDialog Whether to force Spotify consent dialog display.
     * @return Spotify authorization URL.
     */
    fun buildAuthorizationCodeWithPkceUri(
        clientId: String,
        redirectUri: String,
        codeChallenge: String,
        codeChallengeMethod: String = "S256",
        scope: List<String> = emptyList(),
        state: String? = null,
        showDialog: Boolean? = null,
    ): String =
        URLBuilder(authorizeEndpoint)
            .apply {
                parameters.append("response_type", "code")
                parameters.append("client_id", clientId)
                parameters.append("redirect_uri", redirectUri)
                parameters.append("code_challenge", codeChallenge)
                parameters.append("code_challenge_method", codeChallengeMethod)
                if (scope.isNotEmpty()) {
                    parameters.append("scope", scope.joinToString(" "))
                }
                state?.let { parameters.append("state", it) }
                showDialog?.let { parameters.append("show_dialog", it.toString()) }
            }.buildString()

    /**
     * Builds the Spotify Accounts authorization URL for Authorization Code flow.
     *
     * @param clientId Spotify application Client ID.
     * @param redirectUri Redirect URI registered in Spotify Dashboard.
     * @param scope Spotify OAuth scopes requested for this flow.
     * @param state Opaque state value used for CSRF protection during authorization.
     * @param showDialog Whether to force Spotify consent dialog display.
     * @return Spotify authorization URL.
     */
    fun buildAuthorizationCodeUri(
        clientId: String,
        redirectUri: String,
        scope: List<String> = emptyList(),
        state: String? = null,
        showDialog: Boolean? = null,
    ): String =
        URLBuilder(authorizeEndpoint)
            .apply {
                parameters.append("response_type", "code")
                parameters.append("client_id", clientId)
                parameters.append("redirect_uri", redirectUri)
                if (scope.isNotEmpty()) {
                    parameters.append("scope", scope.joinToString(" "))
                }
                state?.let { parameters.append("state", it) }
                showDialog?.let { parameters.append("show_dialog", it.toString()) }
            }.buildString()

    /**
     * Exchanges an authorization code for access and refresh tokens using PKCE.
     *
     * @param clientId Spotify application Client ID.
     * @param code Authorization code issued by Spotify Accounts service.
     * @param redirectUri Redirect URI registered in Spotify Dashboard.
     * @param codeVerifier PKCE code verifier used when exchanging authorization code.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun requestAuthorizationCodeWithPkceToken(
        clientId: String,
        code: String,
        redirectUri: String,
        codeVerifier: String,
    ): SpotifyApiResponse<TokenResponse> {
        val response =
            client.post(tokenEndpoint) {
                accept(ContentType.Application.Json)
                setBody(
                    io.ktor.client.request.forms.FormDataContent(
                        parameters {
                            append("grant_type", "authorization_code")
                            append("code", code)
                            append("redirect_uri", redirectUri)
                            append("client_id", clientId)
                            append("code_verifier", codeVerifier)
                        },
                    ),
                )
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Exchanges an authorization code for access and refresh tokens.
     *
     * @param clientId Spotify application Client ID.
     * @param clientSecret Spotify application Client Secret.
     * @param code Authorization code issued by Spotify Accounts service.
     * @param redirectUri Redirect URI registered in Spotify Dashboard.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun requestAuthorizationCodeToken(
        clientId: String,
        clientSecret: String,
        code: String,
        redirectUri: String,
    ): SpotifyApiResponse<TokenResponse> {
        val response =
            client.post(tokenEndpoint) {
                accept(ContentType.Application.Json)
                header(HttpHeaders.Authorization, basicAuthorization(clientId, clientSecret))
                setBody(
                    io.ktor.client.request.forms.FormDataContent(
                        parameters {
                            append("grant_type", "authorization_code")
                            append("code", code)
                            append("redirect_uri", redirectUri)
                        },
                    ),
                )
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Requests an app-only access token using Client Credentials flow.
     *
     * @param clientId Spotify application Client ID.
     * @param clientSecret Spotify application Client Secret.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun requestClientCredentialsToken(
        clientId: String,
        clientSecret: String,
    ): SpotifyApiResponse<TokenResponse> {
        val response =
            client.post(tokenEndpoint) {
                accept(ContentType.Application.Json)
                header(HttpHeaders.Authorization, basicAuthorization(clientId, clientSecret))
                setBody(
                    io.ktor.client.request.forms.FormDataContent(
                        parameters {
                            append("grant_type", "client_credentials")
                        },
                    ),
                )
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Refreshes an access token for Authorization Code with PKCE flow.
     *
     * @param clientId Spotify application Client ID.
     * @param refreshToken Refresh token used to issue a new access token.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun refreshTokenWithPkce(
        clientId: String,
        refreshToken: String,
    ): SpotifyApiResponse<TokenResponse> {
        val response =
            client.post(tokenEndpoint) {
                accept(ContentType.Application.Json)
                setBody(
                    io.ktor.client.request.forms.FormDataContent(
                        parameters {
                            append("grant_type", "refresh_token")
                            append("refresh_token", refreshToken)
                            append("client_id", clientId)
                        },
                    ),
                )
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Refreshes an access token for Authorization Code flow.
     *
     * @param clientId Spotify application Client ID.
     * @param clientSecret Spotify application Client Secret.
     * @param refreshToken Refresh token used to issue a new access token.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun refreshToken(
        clientId: String,
        clientSecret: String,
        refreshToken: String,
    ): SpotifyApiResponse<TokenResponse> {
        val response =
            client.post(tokenEndpoint) {
                accept(ContentType.Application.Json)
                header(HttpHeaders.Authorization, basicAuthorization(clientId, clientSecret))
                setBody(
                    io.ktor.client.request.forms.FormDataContent(
                        parameters {
                            append("grant_type", "refresh_token")
                            append("refresh_token", refreshToken)
                        },
                    ),
                )
            }
        return response.toSpotifyApiResponse()
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun basicAuthorization(
        clientId: String,
        clientSecret: String,
    ): String {
        val encoded = Base64.encode("$clientId:$clientSecret".encodeToByteArray())
        return "Basic $encoded"
    }
}
