package com.nubasu.spotify.webapi.wrapper.response.search

import com.nubasu.spotify.webapi.wrapper.response.episodes.SimplifiedEpisodeObject
import kotlinx.serialization.Serializable

@Serializable
data class Episodes(
    val href: String,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int,
    val items: List<SimplifiedEpisodeObject>
)
