package com.nubasu.kotlin_spotify_web_api_wrapper.api

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyApiResponse
import io.ktor.client.HttpClient
import io.ktor.http.HttpStatusCode
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.fail

internal object ApiStatusCaseAsserts {
    suspend fun <T> assertStatus201Created(
        successBody: String = "{}",
        invoke: suspend (HttpClient) -> SpotifyApiResponse<T>,
    ) {
        val successResult = runCatching {
            invoke(ApiTestClientFactory.successClient(HttpStatusCode.Created, successBody))
        }

        successResult.getOrNull()?.let { response ->
            assertEquals(201, response.statusCode)
        }
        successResult.exceptionOrNull()?.let { error ->
            if (error is RuntimeException) {
                fail("201 should not be treated as API error, but RuntimeException was thrown: ${error.message}")
            }
        }
    }

    suspend fun <T> assertStatus401Unauthorized(
        invoke: suspend (HttpClient) -> SpotifyApiResponse<T>,
    ) {
        assertFailsWith<RuntimeException> {
            invoke(ApiTestClientFactory.errorClient(HttpStatusCode.Unauthorized))
        }
    }

    suspend fun <T> assertStatus403Forbidden(
        invoke: suspend (HttpClient) -> SpotifyApiResponse<T>,
    ) {
        assertFailsWith<RuntimeException> {
            invoke(ApiTestClientFactory.errorClient(HttpStatusCode.Forbidden))
        }
    }

    suspend fun <T> assertStatus429TooManyRequests(
        invoke: suspend (HttpClient) -> SpotifyApiResponse<T>,
    ) {
        assertFailsWith<RuntimeException> {
            invoke(ApiTestClientFactory.errorClient(HttpStatusCode.TooManyRequests))
        }
    }
}
