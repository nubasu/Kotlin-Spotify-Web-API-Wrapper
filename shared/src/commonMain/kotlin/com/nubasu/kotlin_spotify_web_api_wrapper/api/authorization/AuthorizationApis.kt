package com.nubasu.kotlin_spotify_web_api_wrapper.api.authorization

import com.nubasu.kotlin_spotify_web_api_wrapper.response.authorization.TokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLBuilder
import io.ktor.http.isSuccess
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class AuthorizationApis(
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    },
    private val authorizeEndpoint: String = "https://accounts.spotify.com/authorize",
    private val tokenEndpoint: String = "https://accounts.spotify.com/api/token",
) {

    fun buildAuthorizationCodeWithPkceUri(
        clientId: String,
        redirectUri: String,
        codeChallenge: String,
        codeChallengeMethod: String = "S256",
        scope: List<String> = emptyList(),
        state: String? = null,
        showDialog: Boolean? = null,
    ): String {
        return URLBuilder(authorizeEndpoint).apply {
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
    }

    fun buildAuthorizationCodeUri(
        clientId: String,
        redirectUri: String,
        scope: List<String> = emptyList(),
        state: String? = null,
        showDialog: Boolean? = null,
    ): String {
        return URLBuilder(authorizeEndpoint).apply {
            parameters.append("response_type", "code")
            parameters.append("client_id", clientId)
            parameters.append("redirect_uri", redirectUri)
            if (scope.isNotEmpty()) {
                parameters.append("scope", scope.joinToString(" "))
            }
            state?.let { parameters.append("state", it) }
            showDialog?.let { parameters.append("show_dialog", it.toString()) }
        }.buildString()
    }

    suspend fun requestAuthorizationCodeWithPkceToken(
        clientId: String,
        code: String,
        redirectUri: String,
        codeVerifier: String,
    ): TokenResponse {
        val response = client.post(tokenEndpoint) {
            accept(ContentType.Application.Json)
            setBody(
                io.ktor.client.request.forms.FormDataContent(
                    parameters {
                        append("grant_type", "authorization_code")
                        append("code", code)
                        append("redirect_uri", redirectUri)
                        append("client_id", clientId)
                        append("code_verifier", codeVerifier)
                    }
                )
            )
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify auth error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun requestAuthorizationCodeToken(
        clientId: String,
        clientSecret: String,
        code: String,
        redirectUri: String,
    ): TokenResponse {
        val response = client.post(tokenEndpoint) {
            accept(ContentType.Application.Json)
            header(HttpHeaders.Authorization, basicAuthorization(clientId, clientSecret))
            setBody(
                io.ktor.client.request.forms.FormDataContent(
                    parameters {
                        append("grant_type", "authorization_code")
                        append("code", code)
                        append("redirect_uri", redirectUri)
                    }
                )
            )
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify auth error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun requestClientCredentialsToken(
        clientId: String,
        clientSecret: String,
    ): TokenResponse {
        val response = client.post(tokenEndpoint) {
            accept(ContentType.Application.Json)
            header(HttpHeaders.Authorization, basicAuthorization(clientId, clientSecret))
            setBody(
                io.ktor.client.request.forms.FormDataContent(
                    parameters {
                        append("grant_type", "client_credentials")
                    }
                )
            )
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify auth error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun refreshTokenWithPkce(
        clientId: String,
        refreshToken: String,
    ): TokenResponse {
        val response = client.post(tokenEndpoint) {
            accept(ContentType.Application.Json)
            setBody(
                io.ktor.client.request.forms.FormDataContent(
                    parameters {
                        append("grant_type", "refresh_token")
                        append("refresh_token", refreshToken)
                        append("client_id", clientId)
                    }
                )
            )
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify auth error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun refreshToken(
        clientId: String,
        clientSecret: String,
        refreshToken: String,
    ): TokenResponse {
        val response = client.post(tokenEndpoint) {
            accept(ContentType.Application.Json)
            header(HttpHeaders.Authorization, basicAuthorization(clientId, clientSecret))
            setBody(
                io.ktor.client.request.forms.FormDataContent(
                    parameters {
                        append("grant_type", "refresh_token")
                        append("refresh_token", refreshToken)
                    }
                )
            )
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify auth error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
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
