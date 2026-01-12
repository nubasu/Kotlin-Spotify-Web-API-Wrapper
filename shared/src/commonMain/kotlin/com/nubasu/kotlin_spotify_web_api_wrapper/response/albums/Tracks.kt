package com.nubasu.kotlin_spotify_web_api_wrapper.response.albums

// same as AlbumTracks
data class Tracks(
    val href: String,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int,
    val items: List<SimplifiedTrackObject>,
)
