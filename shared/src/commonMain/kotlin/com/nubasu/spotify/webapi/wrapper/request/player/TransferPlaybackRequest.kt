package com.nubasu.spotify.webapi.wrapper.request.player

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransferPlaybackRequest(
    @SerialName("device_ids")
    val deviceIds: List<String>,
    val play: Boolean? = null,
)
