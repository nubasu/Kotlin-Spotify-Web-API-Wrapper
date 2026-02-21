@file:Suppress("DEPRECATION")

package com.nubasu.kotlin_spotify_web_api_wrapper.auth

import com.nubasu.kotlin_spotify_web_api_wrapper.api.authorization.AuthorizationApis
import com.nubasu.kotlin_spotify_web_api_wrapper.api.authorization.SpotifyAuthManager
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.TokenHolder
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
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SpotifyAuthManagerTest {
    @AfterTest
    fun cleanup() {
        TokenHolder.token = ""
        TokenHolder.tokenProvider = null
    }

    @Test
    fun startPkceAuthorization_generatesStateAndChallenge() {
        val manager = SpotifyAuthManager(
            clientId = "client-id",
            redirectUri = "app://callback",
            authorizationApis = AuthorizationApis(
                client = testHttpClient(MockEngine { error("No network expected") })
            ),
        )

        val req = manager.startPkceAuthorization(scope = listOf("user-read-email"))
        val url = Url(req.authorizationUri)

        assertTrue(req.state.isNotBlank())
        assertEquals(req.state, url.parameters["state"])
        assertEquals("code", url.parameters["response_type"])
        assertNotNull(url.parameters["code_challenge"])
        assertEquals("S256", url.parameters["code_challenge_method"])
    }

    @Test
    fun startPkceAuthorizationAsync_generatesStateAndChallenge() = runTest {
        val manager = SpotifyAuthManager(
            clientId = "client-id",
            redirectUri = "app://callback",
            authorizationApis = AuthorizationApis(
                client = testHttpClient(MockEngine { error("No network expected") })
            ),
        )

        val req = manager.startPkceAuthorizationAsync(scope = listOf("user-read-email"))
        val url = Url(req.authorizationUri)

        assertTrue(req.state.isNotBlank())
        assertEquals(req.state, url.parameters["state"])
        assertEquals("code", url.parameters["response_type"])
        assertNotNull(url.parameters["code_challenge"])
        assertEquals("S256", url.parameters["code_challenge_method"])
    }

    @Test
    fun startPkceAuthorizationAndLaunch_callsLauncher() {
        var launchedUri: String? = null
        val manager = SpotifyAuthManager(
            clientId = "client-id",
            redirectUri = "app://callback",
            authorizationApis = AuthorizationApis(
                client = testHttpClient(MockEngine { error("No network expected") })
            ),
            authorizationUriLauncher = {
                launchedUri = it
                true
            },
        )

        val req = manager.startPkceAuthorizationAndLaunch(scope = listOf("user-read-email"))

        assertEquals(req.authorizationUri, launchedUri)
    }

    @Test
    fun startPkceAuthorizationAsyncAndLaunch_callsLauncher() = runTest {
        var launchedUri: String? = null
        val manager = SpotifyAuthManager(
            clientId = "client-id",
            redirectUri = "app://callback",
            authorizationApis = AuthorizationApis(
                client = testHttpClient(MockEngine { error("No network expected") })
            ),
            authorizationUriLauncher = {
                launchedUri = it
                true
            },
        )

        val req = manager.startPkceAuthorizationAsyncAndLaunch(scope = listOf("user-read-email"))

        assertEquals(req.authorizationUri, launchedUri)
    }

    @Test
    fun buildAuthorizationCodeUriAndLaunch_callsLauncher() {
        var launchedUri: String? = null
        val manager = SpotifyAuthManager(
            clientId = "client-id",
            redirectUri = "app://callback",
            authorizationApis = AuthorizationApis(
                client = testHttpClient(MockEngine { error("No network expected") })
            ),
            authorizationUriLauncher = {
                launchedUri = it
                true
            },
        )

        val uri = manager.buildAuthorizationCodeUriAndLaunch(scope = listOf("user-read-email"))

        assertEquals(uri, launchedUri)
    }

    @Test
    fun buildAuthorizationCodeWithPkceUriAndLaunch_callsLauncher() {
        var launchedUri: String? = null
        val manager = SpotifyAuthManager(
            clientId = "client-id",
            redirectUri = "app://callback",
            authorizationApis = AuthorizationApis(
                client = testHttpClient(MockEngine { error("No network expected") })
            ),
            authorizationUriLauncher = {
                launchedUri = it
                true
            },
        )

        val uri = manager.buildAuthorizationCodeWithPkceUriAndLaunch(codeChallenge = "challenge")

        assertEquals(uri, launchedUri)
    }

    @Test
    fun launchAuthorizationInAppOrBrowser_returnsFalseOnLauncherFailure() {
        val manager = SpotifyAuthManager(
            clientId = "client-id",
            redirectUri = "app://callback",
            authorizationApis = AuthorizationApis(
                client = testHttpClient(MockEngine { error("No network expected") })
            ),
            authorizationUriLauncher = { throw IllegalStateException("boom") },
        )

        val launched = manager.launchAuthorizationInAppOrBrowser("https://accounts.spotify.com/authorize")

        assertFalse(launched)
    }

    @Test
    fun launchAuthorizationInAppOrBrowser_returnsLauncherResult() {
        val manager = SpotifyAuthManager(
            clientId = "client-id",
            redirectUri = "app://callback",
            authorizationApis = AuthorizationApis(
                client = testHttpClient(MockEngine { error("No network expected") })
            ),
            authorizationUriLauncher = { true },
        )

        val launched = manager.launchAuthorizationInAppOrBrowser("https://accounts.spotify.com/authorize")

        assertTrue(launched)
    }

    @Test
    fun buildAuthorizationCodeUris_work() {
        val manager = SpotifyAuthManager(
            clientId = "client-id",
            clientSecret = "secret",
            redirectUri = "app://callback",
            authorizationApis = AuthorizationApis(
                client = testHttpClient(MockEngine { error("No network expected") })
            ),
        )
        val pkceUrl = manager.buildAuthorizationCodeWithPkceUri(codeChallenge = "c")
        val codeUrl = manager.buildAuthorizationCodeUri()
        assertTrue(pkceUrl.contains("code_challenge=c"))
        assertTrue(codeUrl.contains("response_type=code"))
    }

    @Test
    fun completePkceAuthorizationFromRedirectUri_setsTokenHolder() = runTest {
        val engine = MockEngine {
            respond(
                content = """{"access_token":"access-token","token_type":"Bearer","expires_in":3600,"refresh_token":"refresh-token"}""",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
        }
        val manager = SpotifyAuthManager(
            clientId = "client-id",
            redirectUri = "app://callback",
            authorizationApis = AuthorizationApis(client = testHttpClient(engine)),
        )

        val req = manager.startPkceAuthorization()
        val redirected = "app://callback?code=auth-code&state=${req.state}"
        val token = manager.completePkceAuthorizationFromRedirectUri(redirected)

        assertEquals("access-token", token.accessToken)
        assertEquals("access-token", TokenHolder.token)
    }

    @Test
    fun completePkceAuthorization_stateMismatch_throws() {
        runTest {
            val engine = MockEngine {
                respond(
                    content = """{"access_token":"access-token","token_type":"Bearer","expires_in":3600}""",
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
                )
            }
            val manager = SpotifyAuthManager(
                clientId = "client-id",
                redirectUri = "app://callback",
                authorizationApis = AuthorizationApis(client = testHttpClient(engine)),
            )

            manager.startPkceAuthorization()
            assertFailsWith<IllegalStateException> {
                manager.completePkceAuthorization(
                    code = "auth-code",
                    returnedState = "invalid-state",
                )
            }
        }
    }

    @Test
    fun refreshAccessToken_preservesOldRefreshTokenWhenResponseOmitsIt() = runTest {
        var callCount = 0
        val engine = MockEngine {
            callCount++
            val response = if (callCount == 1) {
                """{"access_token":"access-1","token_type":"Bearer","expires_in":1,"refresh_token":"refresh-1"}"""
            } else {
                """{"access_token":"access-2","token_type":"Bearer","expires_in":3600}"""
            }
            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
        }
        val manager = SpotifyAuthManager(
            clientId = "client-id",
            redirectUri = "app://callback",
            authorizationApis = AuthorizationApis(client = testHttpClient(engine)),
        )

        val req = manager.startPkceAuthorization()
        manager.completePkceAuthorization(
            code = "auth-code",
            returnedState = req.state,
        )
        val refreshed = manager.refreshAccessToken()

        assertEquals("access-2", refreshed.accessToken)
        assertEquals("refresh-1", manager.getCurrentToken()?.refreshToken)
    }

    @Test
    fun exchangeAuthorizationCodeWithPkce_setsToken() = runTest {
        val engine = MockEngine {
            respond(
                content = """{"access_token":"pkce-token","token_type":"Bearer","expires_in":3600,"refresh_token":"r1"}""",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
        }
        val manager = SpotifyAuthManager(
            clientId = "client-id",
            redirectUri = "app://callback",
            authorizationApis = AuthorizationApis(client = testHttpClient(engine)),
        )
        val token = manager.exchangeAuthorizationCodeWithPkce("code", "verifier")
        assertEquals("pkce-token", token.accessToken)
        assertEquals("pkce-token", manager.getCurrentToken()?.accessToken)
    }

    @Test
    fun exchangeAuthorizationCode_and_clientCredentials_work() = runTest {
        var call = 0
        val engine = MockEngine {
            call++
            val body = if (call == 1) {
                """{"access_token":"code-token","token_type":"Bearer","expires_in":3600,"refresh_token":"r1"}"""
            } else {
                """{"access_token":"cc-token","token_type":"Bearer","expires_in":3600}"""
            }
            respond(
                content = body,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
        }
        val manager = SpotifyAuthManager(
            clientId = "client-id",
            clientSecret = "secret",
            redirectUri = "app://callback",
            authorizationApis = AuthorizationApis(client = testHttpClient(engine)),
        )
        val t1 = manager.exchangeAuthorizationCode("code")
        val t2 = manager.requestClientCredentialsToken()
        assertEquals("code-token", t1.accessToken)
        assertEquals("cc-token", t2.accessToken)
    }

    @Test
    fun getValidAccessToken_and_clearToken_work() = runTest {
        val engine = MockEngine {
            respond(
                content = """{"access_token":"access-token","token_type":"Bearer","expires_in":3600,"refresh_token":"r1"}""",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
        }
        val manager = SpotifyAuthManager(
            clientId = "client-id",
            redirectUri = "app://callback",
            authorizationApis = AuthorizationApis(client = testHttpClient(engine)),
        )
        val req = manager.startPkceAuthorization()
        manager.completePkceAuthorization("code", req.state)
        val access = manager.getValidAccessToken()
        assertEquals("access-token", access)
        manager.clearToken()
        assertEquals(null, manager.getCurrentToken())
    }

    private fun testHttpClient(engine: MockEngine): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json()
            }
        }
    }
}
