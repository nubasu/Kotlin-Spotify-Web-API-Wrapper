package com.nubasu.spotify.webapi.wrapper.api

import kotlin.test.Test
import kotlin.test.assertEquals

class ApiResponseExtensionsTest {
    @Test
    fun toSpotifyErrorResponse_parsesSpotifyErrorJson() {
        val raw = """{"error":{"status":400,"message":"bad request"}}"""

        val parsed = raw.toSpotifyErrorResponse(statusCode = 400)

        assertEquals(400, parsed.error.status)
        assertEquals("bad request", parsed.error.message)
    }

    @Test
    fun toSpotifyErrorResponse_fallbackWhenJsonIsInvalid() {
        val raw = """not-json"""

        val parsed = raw.toSpotifyErrorResponse(statusCode = 429)

        assertEquals(429, parsed.error.status)
        assertEquals("not-json", parsed.error.message)
    }
}
