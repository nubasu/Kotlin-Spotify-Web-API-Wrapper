package com.nubasu.kotlin_spotify_web_api_wrapper.utils

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyApiResponse
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyResponseData
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class RateLimitHandlingTest {
    @Test
    fun parseRetryAfterSeconds_parsesInteger() {
        assertEquals(3L, RateLimitHandling.parseRetryAfterSeconds("3"))
    }

    @Test
    fun parseRetryAfterSeconds_parsesDecimal() {
        assertEquals(3L, RateLimitHandling.parseRetryAfterSeconds("2.1"))
    }

    @Test
    fun parseRetryAfterSeconds_returnsNull_forInvalidInput() {
        assertNull(RateLimitHandling.parseRetryAfterSeconds("abc"))
        assertNull(RateLimitHandling.parseRetryAfterSeconds(null))
    }

    @Test
    fun retryAfterDelayMillis_readsHeader_caseInsensitive() {
        val millis = RateLimitHandling.retryAfterDelayMillis(
            mapOf("retry-after" to "5")
        )
        assertEquals(5000L, millis)
    }

    @Test
    fun retryAfterDelayMillis_readsFromSpotifyApiResponse() {
        val response = SpotifyApiResponse(
            statusCode = 429,
            data = SpotifyResponseData.Success(true),
            headers = mapOf("Retry-After" to "2"),
        )

        assertEquals(2000L, RateLimitHandling.retryAfterDelayMillis(response))
    }
}
