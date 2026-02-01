package com.nubasu.kotlin_spotify_web_api_wrapper.request.player

data class SeekToPositionRequest(
    val positionMs: Int,
    val deviceId: String? = null,
)