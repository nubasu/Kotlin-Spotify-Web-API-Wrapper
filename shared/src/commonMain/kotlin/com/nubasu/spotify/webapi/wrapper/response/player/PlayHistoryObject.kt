package com.nubasu.spotify.webapi.wrapper.response.player

import com.nubasu.spotify.webapi.wrapper.response.tracks.Track
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayHistoryObject(
    val track: Track? = null,
    @SerialName("played_at")
    val playedAt: String? = null,
    val context: Context? = null,
)
