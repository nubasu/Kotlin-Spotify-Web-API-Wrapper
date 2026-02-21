package com.nubasu.spotify.webapi.wrapper.response.playlists

import kotlinx.serialization.Serializable

@Serializable
data class Playlists(
    val href: String,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int,
    val items: List<SimplifiedPlaylistObject>,
)
