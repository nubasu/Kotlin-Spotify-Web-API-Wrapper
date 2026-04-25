package com.nubasu.spotify.webapi.wrapper.api

import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyError
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyErrorResponse
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyResponseData
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import kotlinx.serialization.decodeFromString

/**
 * Converts a raw HTTP response into a typed Spotify API response.
 *
 * Parse failures on the success body are caught and returned as [SpotifyResponseData.Error].
 *
 * @return Wrapped Spotify API response with status code and parsed Spotify payload.
 */
internal suspend inline fun <reified T> HttpResponse.toSpotifyApiResponse(): SpotifyApiResponse<T> {
    val responseHeaders = headersMap()
    return if (status.isSuccess()) {
        runCatching { SpotifyApiResponse(status.value, SpotifyResponseData.Success(body<T>()), responseHeaders) }
            .getOrElse { e ->
                SpotifyApiResponse(
                    status.value,
                    SpotifyResponseData.Error(SpotifyErrorResponse(SpotifyError(status.value, e.message ?: "Parse error"))),
                    responseHeaders,
                )
            }
    } else {
        val raw = bodyAsText()
        SpotifyApiResponse(status.value, SpotifyResponseData.Error(raw.toSpotifyErrorResponse(status.value)), responseHeaders)
    }
}

/**
 * Converts a raw HTTP response into a nullable typed Spotify API response.
 *
 * Returns `null` as the success value when the response status is 204 No Content.
 *
 * @return Wrapped Spotify API response with status code and parsed Spotify payload, or null on 204.
 */
internal suspend inline fun <reified T> HttpResponse.toNullableSpotifyApiResponse(): SpotifyApiResponse<T?> {
    val responseHeaders = headersMap()
    val data: SpotifyResponseData<T?> = when {
        status == HttpStatusCode.NoContent -> SpotifyResponseData.Success(null)
        status.isSuccess() ->
            runCatching { SpotifyResponseData.Success<T?>(body<T>()) }
                .getOrElse { e ->
                    SpotifyResponseData.Error(SpotifyErrorResponse(SpotifyError(status.value, e.message ?: "Parse error")))
                }
        else -> SpotifyResponseData.Error(bodyAsText().toSpotifyErrorResponse(status.value))
    }
    return SpotifyApiResponse(status.value, data, responseHeaders)
}

/**
 * Converts a raw HTTP response into a Spotify API response with boolean payload.
 *
 * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
 */
internal suspend fun HttpResponse.toSpotifyBooleanApiResponse(): SpotifyApiResponse<Boolean> {
    val responseHeaders = headersMap()
    return if (status.isSuccess()) {
        SpotifyApiResponse(status.value, SpotifyResponseData.Success(true), responseHeaders)
    } else {
        val raw = bodyAsText()
        SpotifyApiResponse(status.value, SpotifyResponseData.Error(raw.toSpotifyErrorResponse(status.value)), responseHeaders)
    }
}

/**
 * Parses Spotify error JSON into a typed Spotify error response.
 *
 * @param statusCode HTTP status code used when constructing an error response.
 * @return Result value of type $rt.
 */
internal fun String.toSpotifyErrorResponse(statusCode: Int): SpotifyErrorResponse =
    runCatching {
        SpotifyJson.decodeFromString<SpotifyErrorResponse>(this)
    }.getOrElse {
        val fallback = this.ifBlank { "Unknown error" }
        SpotifyErrorResponse(
            error =
                SpotifyError(
                    status = statusCode,
                    message = fallback,
                ),
        )
    }

private fun HttpResponse.headersMap(): Map<String, String> =
    headers.names().associateWith { key ->
        headers[key].orEmpty()
    }
