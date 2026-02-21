package com.nubasu.kotlin_spotify_web_api_wrapper.response.playlists

import kotlinx.serialization.Serializable

@Serializable
data class Tracks(
    val href: String,
    val total: Int,
    val limit: Int? = null,
    val next: String? = null,
    val offset: Int? = null,
    val previous: String? = null,
    val items: List<PlaylistTrackObject> = emptyList(),
)
