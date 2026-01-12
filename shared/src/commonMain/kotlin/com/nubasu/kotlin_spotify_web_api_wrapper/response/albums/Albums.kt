package com.nubasu.kotlin_spotify_web_api_wrapper.response.albums

import kotlinx.serialization.Serializable

@Serializable
data class Albums(
    val albums: List<Album>
)