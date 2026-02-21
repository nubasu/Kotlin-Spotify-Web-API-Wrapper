package com.nubasu.spotify.webapi.wrapper.response.tracks

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AudioFeaturesObject(
    val acousticness: Double? = null,
    @SerialName("analysis_url")
    val analysisUrl: String? = null,
    val danceability: Double? = null,
    @SerialName("duration_ms")
    val durationMs: Int? = null,
    val energy: Double? = null,
    val id: String? = null,
    val instrumentalness: Double? = null,
    val key: Int? = null,
    val liveness: Double? = null,
    val loudness: Double? = null,
    val mode: Int? = null,
    val speechiness: Double? = null,
    val tempo: Double? = null,
    @SerialName("time_signature")
    val timeSignature: Int? = null,
    @SerialName("track_href")
    val trackHref: String? = null,
    val type: String? = null,
    val uri: String? = null,
    val valence: Double? = null,
)
