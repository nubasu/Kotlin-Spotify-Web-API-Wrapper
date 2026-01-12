package com.nubasu.kotlin_spotify_web_api_wrapper.response.player

data class DeviceObject (
    val id: String?,
    val isActive: Boolean,
    val isPrivateSession: Boolean,
    val isRestricted: Boolean,
    val name: String,
    val type: String,
    val volumePercent: Int?,
    val supportsVolume: Boolean,
)