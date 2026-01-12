package com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks

data class OneTrackAudioFeatures(
    val acousticness: Float,
    val analysisUrl: String,
    val danceability: Float,
    val durationMs: Int,
    val energy: Float,
    val id: Int,
    val instrumentalness: Float,
    val key: Int,
    val liveness: Float,
    val loudness: Float,
    val mode: Int,
    val speechiness: Float,
    val tempo: Float,
    val timeSignature: Int,
    val trackHref: String,
    val type: String,
    val uri: String,
    val valence: Float,
)
