package com.nubasu.spotify.webapi.wrapper.response.albums

import com.nubasu.spotify.webapi.wrapper.response.artists.SimplifiedAlbumObject
import kotlinx.serialization.Serializable

@Serializable
data class NewReleaseAlbums(
    val href: String,
    val limit: Int,
    val next: String? = null,
    val offset: Int,
    val previous: String? = null,
    val total: Int,
    val items: List<SimplifiedAlbumObject>,
)