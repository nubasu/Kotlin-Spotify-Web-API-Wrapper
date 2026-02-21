package com.nubasu.kotlin_spotify_web_api_wrapper.utils

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyApiResponse
import kotlinx.coroutines.delay
import kotlin.math.pow
import kotlin.random.Random

data class RetryPolicy(
    val maxRetries: Int = 3,
    val baseDelayMillis: Long = 500L,
    val maxDelayMillis: Long = 30_000L,
    val backoffMultiplier: Double = 2.0,
    val jitterMillis: Long = 0L,
    val retryStatusCodes: Set<Int> = setOf(429, 500, 502, 503, 504),
    val respectRetryAfterHeader: Boolean = true,
) {
    init {
        require(maxRetries >= 0) { "maxRetries must be >= 0" }
        require(baseDelayMillis >= 0) { "baseDelayMillis must be >= 0" }
        require(maxDelayMillis >= 0) { "maxDelayMillis must be >= 0" }
        require(backoffMultiplier >= 1.0) { "backoffMultiplier must be >= 1.0" }
        require(jitterMillis >= 0) { "jitterMillis must be >= 0" }
    }

    fun shouldRetry(statusCode: Int): Boolean {
        return statusCode in retryStatusCodes
    }

    fun backoffDelayMillis(retryAttempt: Int): Long {
        require(retryAttempt >= 1) { "retryAttempt must be >= 1" }
        val exp = baseDelayMillis.toDouble() * backoffMultiplier.pow((retryAttempt - 1).toDouble())
        val computed = exp.toLong()
        return computed.coerceAtMost(maxDelayMillis).coerceAtLeast(0L)
    }
}

class SpotifyRetryExecutor(
    private val retryPolicy: RetryPolicy = RetryPolicy(),
    private val delayFn: suspend (Long) -> Unit = { delay(it) },
    private val jitterFn: (Long) -> Long = { max -> if (max <= 0L) 0L else Random.nextLong(0L, max + 1) },
) {
    suspend fun <T> execute(
        request: suspend () -> SpotifyApiResponse<T>,
    ): SpotifyApiResponse<T> {
        var retries = 0
        var response = request()
        while (retryPolicy.shouldRetry(response.statusCode) && retries < retryPolicy.maxRetries) {
            retries += 1
            val baseDelay = if (retryPolicy.respectRetryAfterHeader) {
                RateLimitHandling.retryAfterDelayMillis(response)
            } else {
                null
            } ?: retryPolicy.backoffDelayMillis(retries)

            val jitter = jitterFn(retryPolicy.jitterMillis).coerceAtLeast(0L)
            val delayMillis = (baseDelay + jitter).coerceAtMost(retryPolicy.maxDelayMillis)
            delayFn(delayMillis)
            response = request()
        }
        return response
    }
}
