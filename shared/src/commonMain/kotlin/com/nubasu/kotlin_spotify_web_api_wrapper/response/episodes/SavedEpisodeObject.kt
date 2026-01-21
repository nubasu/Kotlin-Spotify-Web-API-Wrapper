package com.nubasu.kotlin_spotify_web_api_wrapper.response.episodes

import kotlinx.serialization.Serializable

@Serializable
data class SavedEpisodeObject(
    val addedAt: String? = null,
    val episode: Episode,
)
