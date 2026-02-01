package com.nubasu.kotlin_spotify_web_api_wrapper.response.player

import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.Track
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayHistoryObject(
    val track: Track? = null,
    @SerialName("played_at")
    val playedAt: String? = null,
    val context: Context? = null,
)
