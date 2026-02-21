package com.nubasu.spotify.webapi.wrapper.response.users

import com.nubasu.spotify.webapi.wrapper.response.Cursors
import com.nubasu.spotify.webapi.wrapper.response.artists.ArtistObject
import kotlinx.serialization.Serializable

@Serializable
data class Artists(
    val href: String,
    val limit: Int,
    val next: String,
    val cursors: Cursors,
    val total: Int,
    val items: List<ArtistObject>,
)
