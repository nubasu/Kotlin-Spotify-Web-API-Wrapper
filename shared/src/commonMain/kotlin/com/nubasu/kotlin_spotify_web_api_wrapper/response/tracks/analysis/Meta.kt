package com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.analysis

data class Meta(
    val analyzerVersion: String,
    val platform: String,
    val detailedStatus: String,
    val statusCode: Int,
    val timestamp: Int,
    val analysisTime: Double,
    val inputProcess: String,
)
