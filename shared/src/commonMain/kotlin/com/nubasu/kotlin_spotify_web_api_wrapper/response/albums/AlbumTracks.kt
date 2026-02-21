package com.nubasu.spotify.webapi.wrapper.response.albums

import com.nubasu.spotify.webapi.wrapper.response.artists.SimplifiedAlbumObject
import kotlinx.serialization.Serializable

// same as Tracks
@Serializable
data class AlbumTracks(
    val href: String,
    val limit: Int,
    val next: String? = null,
    val offset: Int,
    val previous: String? = null,
    val total: Int,
    val items: List<SimplifiedTrackObject>,
)
