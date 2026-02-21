package com.nubasu.spotify.webapi.wrapper.response.player

import kotlinx.serialization.Serializable

@Serializable
data class AvailableDevices(
    val devices: List<Device>,
)
