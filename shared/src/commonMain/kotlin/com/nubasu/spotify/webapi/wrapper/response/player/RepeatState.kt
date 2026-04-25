package com.nubasu.spotify.webapi.wrapper.response.player

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Spotify repeat state for a playback context. */
@Serializable
enum class RepeatState {
    @SerialName("off")
    OFF,

    @SerialName("track")
    TRACK,

    @SerialName("context")
    CONTEXT,
}
