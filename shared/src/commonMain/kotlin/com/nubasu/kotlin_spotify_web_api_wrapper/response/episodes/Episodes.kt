package com.nubasu.kotlin_spotify_web_api_wrapper.response.episodes

import kotlinx.serialization.Serializable

@Serializable
data class Episodes(
    val episodes: List<Episode>,
)
