package com.nubasu.spotify.webapi.wrapper.response.tracks.analysis

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Segments(
    val start: Double? = null,
    val duration: Double? = null,
    val confidence: Double? = null,
    val loudness: Double? = null,
    @SerialName("loudness_start")
    val loudnessStart: Double? = null,
    @SerialName("loudness_max")
    val loudnessMax: Double? = null,
    @SerialName("loudness_max_time")
    val loudnessMaxTime: Double? = null,
    @SerialName("loudness_end")
    val loudnessEnd: Double? = null,
    val pitches: List<Double> = emptyList(),
    val timbre: List<Double> = emptyList(),
)

