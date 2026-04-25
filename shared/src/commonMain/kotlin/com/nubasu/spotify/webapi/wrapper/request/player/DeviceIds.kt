package com.nubasu.spotify.webapi.wrapper.request.player

import kotlinx.serialization.Serializable

@Serializable
data class DeviceIds(
    val ids: List<String>,
) {
    init {
        require(ids.isNotEmpty()) { "ids must not be empty" }
    }
}
