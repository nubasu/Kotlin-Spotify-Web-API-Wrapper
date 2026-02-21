package com.nubasu.kotlin_spotify_web_api_wrapper.auth

import com.nubasu.kotlin_spotify_web_api_wrapper.api.authorization.AuthorizationApis
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest

class AuthorizationApisTest {
    @Test
    fun buildAuthorizationCodeWithPkceUri_hasExpectedParams() {
        val apis = AuthorizationApis(
            client = testHttpClient(MockEngine { error("No network expected") })
        )

        val uri = apis.buildAuthorizationCodeWithPkceUri(
            clientId = "client-id",
            redirectUri = "app://callback",
            codeChallenge = "challenge",
            scope = listOf("user-read-email", "user-read-private"),
            state = "state-1",
            showDialog = true,
        )

        val url = Url(uri)
        assertEquals("https", url.protocol.name.lowercase())
        assertEquals("accounts.spotify.com", url.host)
        assertEquals("/authorize", url.encodedPath)
        assertEquals("code", url.parameters["response_type"])
        assertEquals("client-id", url.parameters["client_id"])
        assertEquals("app://callback", url.parameters["redirect_uri"])
        assertEquals("challenge", url.parameters["code_challenge"])
        assertEquals("S256", url.parameters["code_challenge_method"])
        assertEquals("state-1", url.parameters["state"])
        assertEquals("true", url.parameters["show_dialog"])
        assertEquals("user-read-email user-read-private", url.parameters["scope"])
    }

    @Test
    fun buildAuthorizationCodeUri_hasExpectedParams() {
        val apis = AuthorizationApis(
            client = testHttpClient(MockEngine { error("No network expected") })
        )

        val uri = apis.buildAuthorizationCodeUri(
            clientId = "client-id",
            redirectUri = "https://example.com/callback",
            scope = listOf("playlist-read-private"),
            state = "state-2",
        )

        val url = Url(uri)
        assertEquals("/authorize", url.encodedPath)
        assertEquals("code", url.parameters["response_type"])
        assertEquals("client-id", url.parameters["client_id"])
        assertEquals("https://example.com/callback", url.parameters["redirect_uri"])
        assertEquals("playlist-read-private", url.parameters["scope"])
        assertEquals("state-2", url.parameters["state"])
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun requestClientCredentialsToken_sendsBasicAuthAndParsesResponse() = runTest {
        var authHeader: String? = null
        val engine = MockEngine { request ->
            authHeader = request.headers[HttpHeaders.Authorization]
            respond(
                content = """{"access_token":"access-token","token_type":"Bearer","expires_in":3600}""",
                status = HttpStatusCode.Created,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
        }
        val apis = AuthorizationApis(
            client = testHttpClient(engine),
            tokenEndpoint = "https://accounts.spotify.com/api/token",
        )

        val token = apis.requestClientCredentialsToken(
            clientId = "client-id",
            clientSecret = "client-secret",
        )

        val expected = "Basic " + Base64.encode("client-id:client-secret".encodeToByteArray())
        assertEquals(expected, authHeader)
        assertEquals("access-token", token.accessToken)
        assertEquals("Bearer", token.tokenType)
        assertEquals(3600, token.expiresIn)
        assertNull(token.refreshToken)
    }
    @Test
    fun requestClientCredentialsToken_status201_created() = runTest {
        assertStatus201Created { apis ->
            apis.requestClientCredentialsToken("client-id", "secret")
        }
    }

    @Test
    fun requestClientCredentialsToken_status401_unauthorized() = runTest {
        assertStatus401Unauthorized { apis ->
            apis.requestClientCredentialsToken("client-id", "secret")
        }
    }

    @Test
    fun requestClientCredentialsToken_status403_forbidden() = runTest {
        assertStatus403Forbidden { apis ->
            apis.requestClientCredentialsToken("client-id", "secret")
        }
    }

    @Test
    fun requestClientCredentialsToken_status429_tooManyRequests() = runTest {
        assertStatus429TooManyRequests { apis ->
            apis.requestClientCredentialsToken("client-id", "secret")
        }
    }

    @Test
    fun requestAuthorizationCodeWithPkceToken_parsesRefreshToken() = runTest {
        var authHeader: String? = null
        val engine = MockEngine { request ->
            authHeader = request.headers[HttpHeaders.Authorization]
            respond(
                content = """{"access_token":"pkce-access","token_type":"Bearer","expires_in":3600,"refresh_token":"pkce-refresh","scope":"user-read-email"}""",
                status = HttpStatusCode.Created,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
        }
        val apis = AuthorizationApis(client = testHttpClient(engine))

        val token = apis.requestAuthorizationCodeWithPkceToken(
            clientId = "client-id",
            code = "authorization-code",
            redirectUri = "app://callback",
            codeVerifier = "verifier",
        )

        assertNull(authHeader)
        assertEquals("pkce-access", token.accessToken)
        assertEquals("pkce-refresh", token.refreshToken)
        assertEquals("user-read-email", token.scope)
    }
    @Test
    fun requestAuthorizationCodeWithPkceToken_status201_created() = runTest {
        assertStatus201Created { apis ->
            apis.requestAuthorizationCodeWithPkceToken(
                clientId = "client-id",
                code = "authorization-code",
                redirectUri = "app://callback",
                codeVerifier = "verifier",
            )
        }
    }

    @Test
    fun requestAuthorizationCodeWithPkceToken_status401_unauthorized() = runTest {
        assertStatus401Unauthorized { apis ->
            apis.requestAuthorizationCodeWithPkceToken(
                clientId = "client-id",
                code = "authorization-code",
                redirectUri = "app://callback",
                codeVerifier = "verifier",
            )
        }
    }

    @Test
    fun requestAuthorizationCodeWithPkceToken_status403_forbidden() = runTest {
        assertStatus403Forbidden { apis ->
            apis.requestAuthorizationCodeWithPkceToken(
                clientId = "client-id",
                code = "authorization-code",
                redirectUri = "app://callback",
                codeVerifier = "verifier",
            )
        }
    }

    @Test
    fun requestAuthorizationCodeWithPkceToken_status429_tooManyRequests() = runTest {
        assertStatus429TooManyRequests { apis ->
            apis.requestAuthorizationCodeWithPkceToken(
                clientId = "client-id",
                code = "authorization-code",
                redirectUri = "app://callback",
                codeVerifier = "verifier",
            )
        }
    }

    @Test
    fun requestAuthorizationCodeToken_parsesToken() = runTest {
        val engine = MockEngine {
            respond(
                content = """{"access_token":"access","token_type":"Bearer","expires_in":3600,"refresh_token":"refresh"}""",
                status = HttpStatusCode.Created,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
        }
        val apis = AuthorizationApis(client = testHttpClient(engine))

        val token = apis.requestAuthorizationCodeToken(
            clientId = "client-id",
            clientSecret = "secret",
            code = "code",
            redirectUri = "app://callback",
        )
        assertEquals("access", token.accessToken)
        assertEquals("refresh", token.refreshToken)
    }
    @Test
    fun requestAuthorizationCodeToken_status201_created() = runTest {
        assertStatus201Created { apis ->
            apis.requestAuthorizationCodeToken(
                clientId = "client-id",
                clientSecret = "secret",
                code = "code",
                redirectUri = "app://callback",
            )
        }
    }

    @Test
    fun requestAuthorizationCodeToken_status401_unauthorized() = runTest {
        assertStatus401Unauthorized { apis ->
            apis.requestAuthorizationCodeToken(
                clientId = "client-id",
                clientSecret = "secret",
                code = "code",
                redirectUri = "app://callback",
            )
        }
    }

    @Test
    fun requestAuthorizationCodeToken_status403_forbidden() = runTest {
        assertStatus403Forbidden { apis ->
            apis.requestAuthorizationCodeToken(
                clientId = "client-id",
                clientSecret = "secret",
                code = "code",
                redirectUri = "app://callback",
            )
        }
    }

    @Test
    fun requestAuthorizationCodeToken_status429_tooManyRequests() = runTest {
        assertStatus429TooManyRequests { apis ->
            apis.requestAuthorizationCodeToken(
                clientId = "client-id",
                clientSecret = "secret",
                code = "code",
                redirectUri = "app://callback",
            )
        }
    }

    @Test
    fun refreshTokenWithPkce_returnsToken() = runTest {
        val engine = MockEngine {
            respond(
                content = """{"access_token":"new-access","token_type":"Bearer","expires_in":3600}""",
                status = HttpStatusCode.Created,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
        }
        val apis = AuthorizationApis(client = testHttpClient(engine))

        val token = apis.refreshTokenWithPkce(
            clientId = "client-id",
            refreshToken = "refresh-token",
        )

        assertEquals("new-access", token.accessToken)
        assertNotNull(token.tokenType)
        assertTrue(token.expiresIn > 0)
    }
    @Test
    fun refreshTokenWithPkce_status201_created() = runTest {
        assertStatus201Created { apis ->
            apis.refreshTokenWithPkce(
                clientId = "client-id",
                refreshToken = "refresh-token",
            )
        }
    }

    @Test
    fun refreshTokenWithPkce_status401_unauthorized() = runTest {
        assertStatus401Unauthorized { apis ->
            apis.refreshTokenWithPkce(
                clientId = "client-id",
                refreshToken = "refresh-token",
            )
        }
    }

    @Test
    fun refreshTokenWithPkce_status403_forbidden() = runTest {
        assertStatus403Forbidden { apis ->
            apis.refreshTokenWithPkce(
                clientId = "client-id",
                refreshToken = "refresh-token",
            )
        }
    }

    @Test
    fun refreshTokenWithPkce_status429_tooManyRequests() = runTest {
        assertStatus429TooManyRequests { apis ->
            apis.refreshTokenWithPkce(
                clientId = "client-id",
                refreshToken = "refresh-token",
            )
        }
    }

    @Test
    fun refreshToken_returnsToken() = runTest {
        val engine = MockEngine {
            respond(
                content = """{"access_token":"new-access","token_type":"Bearer","expires_in":3600}""",
                status = HttpStatusCode.Created,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
        }
        val apis = AuthorizationApis(client = testHttpClient(engine))

        val token = apis.refreshToken(
            clientId = "client-id",
            clientSecret = "secret",
            refreshToken = "refresh-token",
        )
        assertEquals("new-access", token.accessToken)
    }
    @Test
    fun refreshToken_status201_created() = runTest {
        assertStatus201Created { apis ->
            apis.refreshToken(
                clientId = "client-id",
                clientSecret = "secret",
                refreshToken = "refresh-token",
            )
        }
    }

    @Test
    fun refreshToken_status401_unauthorized() = runTest {
        assertStatus401Unauthorized { apis ->
            apis.refreshToken(
                clientId = "client-id",
                clientSecret = "secret",
                refreshToken = "refresh-token",
            )
        }
    }

    @Test
    fun refreshToken_status403_forbidden() = runTest {
        assertStatus403Forbidden { apis ->
            apis.refreshToken(
                clientId = "client-id",
                clientSecret = "secret",
                refreshToken = "refresh-token",
            )
        }
    }

    @Test
    fun refreshToken_status429_tooManyRequests() = runTest {
        assertStatus429TooManyRequests { apis ->
            apis.refreshToken(
                clientId = "client-id",
                clientSecret = "secret",
                refreshToken = "refresh-token",
            )
        }
    }

    private suspend fun assertStatus201Created(
        call: suspend (AuthorizationApis) -> Unit,
    ) {
        val success = AuthorizationApis(client = testHttpClient(statusEngine(HttpStatusCode.Created)))
        call(success)
    }

    private suspend fun assertStatus401Unauthorized(
        call: suspend (AuthorizationApis) -> Unit,
    ) {
        val unauthorized = AuthorizationApis(client = testHttpClient(statusEngine(HttpStatusCode.Unauthorized)))
        assertFailsWith<RuntimeException> { call(unauthorized) }
    }

    private suspend fun assertStatus403Forbidden(
        call: suspend (AuthorizationApis) -> Unit,
    ) {
        val forbidden = AuthorizationApis(client = testHttpClient(statusEngine(HttpStatusCode.Forbidden)))
        assertFailsWith<RuntimeException> { call(forbidden) }
    }

    private suspend fun assertStatus429TooManyRequests(
        call: suspend (AuthorizationApis) -> Unit,
    ) {
        val tooMany = AuthorizationApis(client = testHttpClient(statusEngine(HttpStatusCode.TooManyRequests)))
        assertFailsWith<RuntimeException> { call(tooMany) }
    }

    private fun statusEngine(status: HttpStatusCode): MockEngine {
        val body = if (status == HttpStatusCode.Created) {
            """{"access_token":"t","token_type":"Bearer","expires_in":3600,"refresh_token":"r"}"""
        } else {
            """{"error":"x"}"""
        }
        return MockEngine {
            respond(
                content = body,
                status = status,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
        }
    }

    private fun testHttpClient(engine: MockEngine): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json()
            }
        }
    }
}
