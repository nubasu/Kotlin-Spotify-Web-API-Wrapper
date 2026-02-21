package com.nubasu.kotlin_spotify_web_api_wrapper.response.common

data class SpotifyApiResponse<T>(
    val statusCode: Int,
    val data: T,
)
