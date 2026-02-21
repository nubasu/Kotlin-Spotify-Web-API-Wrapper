package com.nubasu.spotify.webapi.wrapper.utils

import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import kotlin.math.ceil

object RateLimitHandling {
    const val RETRY_AFTER_HEADER = "Retry-After"

    fun parseRetryAfterSeconds(value: String?): Long? {
        val trimmed = value?.trim()?.takeIf { it.isNotEmpty() } ?: return null
        val seconds =
            trimmed.toLongOrNull()
                ?: trimmed.toDoubleOrNull()?.let { ceil(it).toLong() }
                ?: return null
        return if (seconds >= 0) seconds else null
    }

    fun retryAfterDelayMillis(headers: Map<String, String>): Long? {
        val retryAfter =
            headers.entries
                .firstOrNull { (name, _) ->
                    name.equals(RETRY_AFTER_HEADER, ignoreCase = true)
                }?.value
        return parseRetryAfterSeconds(retryAfter)?.times(1000L)
    }

    fun retryAfterDelayMillis(response: SpotifyApiResponse<*>): Long? = retryAfterDelayMillis(response.headers)
}
