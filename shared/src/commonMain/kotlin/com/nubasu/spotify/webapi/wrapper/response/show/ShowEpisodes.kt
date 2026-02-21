package com.nubasu.spotify.webapi.wrapper.response.show

import com.nubasu.spotify.webapi.wrapper.response.episodes.SimplifiedEpisodeObject
import kotlinx.serialization.Serializable

@Serializable
data class ShowEpisodes(
    val href: String,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int,
    val items: List<SimplifiedEpisodeObject>,
)
