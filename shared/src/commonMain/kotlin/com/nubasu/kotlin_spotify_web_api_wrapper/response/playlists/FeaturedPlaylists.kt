package com.nubasu.kotlin_spotify_web_api_wrapper.response.playlists

import kotlinx.serialization.Serializable

@Serializable
data class FeaturedPlaylists(
    val message: String,
    val playlists: Playlists,
)
