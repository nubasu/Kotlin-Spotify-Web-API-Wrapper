package com.nubasu.spotify.webapi.wrapper.request.player

data class SeekToPositionRequest(
    val positionMs: Int,
    val deviceId: String? = null,
)
