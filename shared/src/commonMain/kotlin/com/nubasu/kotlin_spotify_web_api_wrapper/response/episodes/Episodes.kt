package com.nubasu.spotify.webapi.wrapper.response.episodes

import kotlinx.serialization.Serializable

@Serializable
data class Episodes(
    val episodes: List<Episode>,
)
