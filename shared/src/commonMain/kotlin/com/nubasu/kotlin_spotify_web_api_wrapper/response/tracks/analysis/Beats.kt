package com.nubasu.spotify.webapi.wrapper.response.tracks.analysis

import kotlinx.serialization.Serializable

@Serializable
data class Beats(
    val start: Double? = null,
    val duration: Double? = null,
    val confidence: Double? = null,
)
