package com.nubasu.spotify.webapi.wrapper.response.playlists

import kotlinx.serialization.Serializable

@Serializable
data class FeaturedPlaylists(
    val message: String,
    val playlists: Playlists,
)
