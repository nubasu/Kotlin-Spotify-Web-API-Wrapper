package com.nubasu.spotify.webapi.wrapper.response.episodes

import kotlinx.serialization.Serializable

@Serializable
data class SavedEpisodeObject(
    val addedAt: String? = null,
    val episode: Episode,
)
