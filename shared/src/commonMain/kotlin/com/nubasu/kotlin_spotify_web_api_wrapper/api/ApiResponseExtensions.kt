package com.nubasu.kotlin_spotify_web_api_wrapper.api

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyApiResponse
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyError
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyErrorResponse
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyResponseData
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val spotifyResponseJson = Json { ignoreUnknownKeys = true }

internal suspend inline fun <reified T> HttpResponse.toSpotifyApiResponse(): SpotifyApiResponse<T> {
    return if (status.isSuccess()) {
        SpotifyApiResponse(status.value, SpotifyResponseData.Success(body()))
    } else {
        val raw = bodyAsText()
        SpotifyApiResponse(status.value, SpotifyResponseData.Error(raw.toSpotifyErrorResponse(status.value)))
    }
}

internal suspend fun HttpResponse.toSpotifyBooleanApiResponse(): SpotifyApiResponse<Boolean> {
    return if (status.isSuccess()) {
        SpotifyApiResponse(status.value, SpotifyResponseData.Success(true))
    } else {
        val raw = bodyAsText()
        SpotifyApiResponse(status.value, SpotifyResponseData.Error(raw.toSpotifyErrorResponse(status.value)))
    }
}

internal fun String.toSpotifyErrorResponse(statusCode: Int): SpotifyErrorResponse {
    return runCatching {
        spotifyResponseJson.decodeFromString<SpotifyErrorResponse>(this)
    }.getOrElse {
        val fallback = this.ifBlank { "Unknown error" }
        SpotifyErrorResponse(
            error = SpotifyError(
                status = statusCode,
                message = fallback,
            )
        )
    }
}
