package com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.analysis

data class Segments(
    val start: Double,
    val duration: Double,
    val confidence: Double,
    val loudness: Double,
    val loudnessStart: Double,
    val loudnessMax: Double,
    val loudnessMaxTime: Double,
    val loudnessEnd: Double,
    val pitches: List<Double>,
    val timbre: List<Double>,
)

