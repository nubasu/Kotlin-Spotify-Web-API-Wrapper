package com.nubasu.spotify.webapi.wrapper.request.player

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartResumePlaybackRequest(
    @SerialName("context_uri")
    val contextUri: String? = null,
    val uris: List<String>? = null,
    val offset: Offset? = null,
    @SerialName("position_ms")
    val positionMs: Int? = null,
)
