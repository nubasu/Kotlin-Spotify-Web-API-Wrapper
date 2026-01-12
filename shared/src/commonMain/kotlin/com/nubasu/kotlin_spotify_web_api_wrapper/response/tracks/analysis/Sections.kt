package com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.analysis

data class Sections(
    val start: Double,
    val duration: Double,
    val confidence: Double,
    val loudness: Double,
    val tempo: Double,
    val tempoConfidence: Double,
    val key: Int,
    val keyConfidence: Double,
    val mode: Double,
    val modeConfidence: Double,
    val timeSignature: Int,
    val timeSignatureConfidence: Double,
)
