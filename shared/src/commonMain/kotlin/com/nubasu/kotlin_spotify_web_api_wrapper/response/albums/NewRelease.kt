package com.nubasu.kotlin_spotify_web_api_wrapper.response.albums

import kotlinx.serialization.Serializable

@Serializable
data class NewRelease(
    val albums: NewReleaseAlbums
)
