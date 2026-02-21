package com.nubasu.kotlin_spotify_web_api_wrapper.response.show

import com.nubasu.kotlin_spotify_web_api_wrapper.response.episodes.SimplifiedEpisodeObject
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
