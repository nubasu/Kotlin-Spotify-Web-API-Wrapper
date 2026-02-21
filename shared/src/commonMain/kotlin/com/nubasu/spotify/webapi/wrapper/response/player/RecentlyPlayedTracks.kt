package com.nubasu.spotify.webapi.wrapper.response.player

import com.nubasu.spotify.webapi.wrapper.response.Cursors
import kotlinx.serialization.Serializable

@Serializable
data class RecentlyPlayedTracks(
    val href: String? = null,
    val limit: Int? = null,
    val next: String? = null,
    val cursors: Cursors? = null,
    val total: Int? = null,
    val items: List<PlayHistoryObject> = listOf(),
)
