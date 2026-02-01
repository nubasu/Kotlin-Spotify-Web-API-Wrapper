package com.nubasu.kotlin_spotify_web_api_wrapper.request.player

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransferPlaybackRequest(
    @SerialName("device_ids")
    val deviceIds: List<String>,
    val play: Boolean? = null
)