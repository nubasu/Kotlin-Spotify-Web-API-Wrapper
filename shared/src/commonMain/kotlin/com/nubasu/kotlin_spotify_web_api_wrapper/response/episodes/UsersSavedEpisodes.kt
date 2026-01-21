package com.nubasu.kotlin_spotify_web_api_wrapper.response.episodes

import kotlinx.serialization.Serializable

@Serializable
data class UsersSavedEpisodes(
    val href: String,
    val limit: Int,
    val next: String? = null,
    val offset: Int,
    val previous: String? = null,
    val total: Int,
    val items: List<SavedEpisodeObject>,
)
