package com.nubasu.spotify.webapi.wrapper.response.playlists

import kotlinx.serialization.Serializable

@Serializable
data class CategorysPlaylists(
    val message: String? = null,
    val playlists: Playlists,
)
