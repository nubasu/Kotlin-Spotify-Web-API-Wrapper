package com.nubasu.spotify.webapi.wrapper.response.common

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SpotifyApiResponseTest {
    @Test
    fun header_returnsValue_caseInsensitive() {
        val response = SpotifyApiResponse(
            statusCode = 429,
            data = SpotifyResponseData.Error(
                SpotifyErrorResponse(
                    error = SpotifyError(
                        status = 429,
                        message = "too many requests",
                    )
                )
            ),
            headers = mapOf("Retry-After" to "2"),
        )

        assertEquals("2", response.header("Retry-After"))
        assertEquals("2", response.header("retry-after"))
    }

    @Test
    fun header_returnsNull_whenMissing() {
        val response = SpotifyApiResponse(
            statusCode = 201,
            data = SpotifyResponseData.Success(true),
        )

        assertNull(response.header("Retry-After"))
    }
}
