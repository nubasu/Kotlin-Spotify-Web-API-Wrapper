package com.nubasu.spotify.webapi.wrapper.response.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyErrorResponse(
    @SerialName("error")
    val error: SpotifyError,
)

@Serializable
data class SpotifyError(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
)

sealed class SpotifyResponseData<out T> {
    data class Success<T>(
        val value: T,
    ) : SpotifyResponseData<T>()

    data class Error(
        val value: SpotifyErrorResponse,
    ) : SpotifyResponseData<Nothing>()
}

data class SpotifyApiResponse<T>(
    val statusCode: Int,
    val data: SpotifyResponseData<T>,
    val headers: Map<String, String> = emptyMap(),
)

fun SpotifyApiResponse<*>.header(name: String): String? =
    headers.entries
        .firstOrNull { (key, _) ->
            key.equals(name, ignoreCase = true)
        }?.value
