package com.nubasu.kotlin_spotify_web_api_wrapper.response.albums

import kotlinx.serialization.Serializable

// same as AlbumTracks
@Serializable
data class Tracks(
    val href: String,
    val limit: Int,
    val next: String? = null,
    val offset: Int,
    val previous: String? = null,
    val total: Int,
    val items: List<SimplifiedTrackObject>,
)
