package com.nubasu.spotify.webapi.wrapper.request.player

import kotlinx.serialization.Serializable

@Serializable
data class DeviceIds(
    val ids: List<String>,
)
