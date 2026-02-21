package com.nubasu.kotlin_spotify_web_api_wrapper.response.playlists

import com.nubasu.kotlin_spotify_web_api_wrapper.response.player.PlaybackItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistTrackObject(
    @SerialName("added_at")
    val addedAt: String,
    @SerialName("added_by")
    val addedBy: AddedBy,
    @SerialName("is_local")
    val isLocal: Boolean,
    val track: PlaybackItem,
)
