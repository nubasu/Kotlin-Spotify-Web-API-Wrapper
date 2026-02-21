package com.nubasu.kotlin_spotify_web_api_wrapper.utils

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyApiResponse
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyError
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyErrorResponse
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyResponseData
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RetryPoliciesTest {
    @Test
    fun retryPolicy_shouldRetry_checksConfiguredStatusCodes() {
        val policy = RetryPolicy(retryStatusCodes = setOf(429, 503))
        assertEquals(true, policy.shouldRetry(429))
        assertEquals(false, policy.shouldRetry(401))
    }

    @Test
    fun retryPolicy_backoffDelayMillis_appliesBackoffAndCap() {
        val policy = RetryPolicy(
            baseDelayMillis = 100,
            maxDelayMillis = 250,
            backoffMultiplier = 2.0,
        )
        assertEquals(100L, policy.backoffDelayMillis(1))
        assertEquals(200L, policy.backoffDelayMillis(2))
        assertEquals(250L, policy.backoffDelayMillis(3))
    }

    @Test
    fun retryExecutor_execute_retriesUntilSuccess() = runTest {
        val delays = mutableListOf<Long>()
        val executor = SpotifyRetryExecutor(
            retryPolicy = RetryPolicy(maxRetries = 3, baseDelayMillis = 100, jitterMillis = 0),
            delayFn = { delays += it },
        )
        var calls = 0
        val response = executor.execute {
            calls += 1
            if (calls < 3) {
                SpotifyApiResponse(
                    statusCode = 429,
                    data = SpotifyResponseData.Error(
                        SpotifyErrorResponse(SpotifyError(429, "too many requests"))
                    ),
                )
            } else {
                SpotifyApiResponse(
                    statusCode = 201,
                    data = SpotifyResponseData.Success("ok"),
                )
            }
        }

        assertEquals(3, calls)
        assertEquals(listOf(100L, 200L), delays)
        assertEquals(201, response.statusCode)
    }

    @Test
    fun retryExecutor_execute_respectsRetryAfterHeader() = runTest {
        val delays = mutableListOf<Long>()
        val executor = SpotifyRetryExecutor(
            retryPolicy = RetryPolicy(maxRetries = 1, baseDelayMillis = 100, jitterMillis = 0),
            delayFn = { delays += it },
        )
        var calls = 0
        executor.execute {
            calls += 1
            if (calls == 1) {
                SpotifyApiResponse(
                    statusCode = 429,
                    data = SpotifyResponseData.Error(
                        SpotifyErrorResponse(SpotifyError(429, "too many requests"))
                    ),
                    headers = mapOf("Retry-After" to "3"),
                )
            } else {
                SpotifyApiResponse(
                    statusCode = 201,
                    data = SpotifyResponseData.Success("ok"),
                )
            }
        }

        assertEquals(listOf(3000L), delays)
    }

    @Test
    fun retryExecutor_execute_stopsAtMaxRetries() = runTest {
        val delays = mutableListOf<Long>()
        val executor = SpotifyRetryExecutor(
            retryPolicy = RetryPolicy(maxRetries = 2, baseDelayMillis = 50, jitterMillis = 0),
            delayFn = { delays += it },
        )
        var calls = 0
        val response = executor.execute {
            calls += 1
            SpotifyApiResponse(
                statusCode = 503,
                data = SpotifyResponseData.Error(
                    SpotifyErrorResponse(SpotifyError(503, "service unavailable"))
                ),
            )
        }

        assertEquals(3, calls)
        assertEquals(listOf(50L, 100L), delays)
        assertTrue(response.data is SpotifyResponseData.Error)
    }
}
