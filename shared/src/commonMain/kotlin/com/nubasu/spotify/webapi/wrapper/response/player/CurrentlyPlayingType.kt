package com.nubasu.spotify.webapi.wrapper.response.player

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Type of the currently playing item returned by the Spotify Player API. */
@Serializable
enum class CurrentlyPlayingType {
    @SerialName("track")
    TRACK,

    @SerialName("episode")
    EPISODE,

    @SerialName("ad")
    AD,

    @SerialName("unknown")
    UNKNOWN,
}
