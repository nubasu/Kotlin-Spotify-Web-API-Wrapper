package com.nubasu.spotify.webapi.wrapper.response.playlists

import kotlinx.serialization.Serializable

@Serializable
data class CategoryPlaylists(
    val message: String? = null,
    val playlists: Playlists,
)
