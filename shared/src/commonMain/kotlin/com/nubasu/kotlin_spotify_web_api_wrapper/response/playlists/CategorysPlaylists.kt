package com.nubasu.kotlin_spotify_web_api_wrapper.response.playlists

import kotlinx.serialization.Serializable

@Serializable
data class CategorysPlaylists(
    val message: String? = null,
    val playlists: Playlists,
)
