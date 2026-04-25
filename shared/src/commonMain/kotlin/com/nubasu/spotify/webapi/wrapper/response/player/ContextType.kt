package com.nubasu.spotify.webapi.wrapper.response.player

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Type of playback context returned by the Spotify Player API. */
@Serializable
enum class ContextType {
    @SerialName("album")
    ALBUM,

    @SerialName("artist")
    ARTIST,

    @SerialName("playlist")
    PLAYLIST,

    @SerialName("show")
    SHOW,

    @SerialName("episode")
    EPISODE,
}
