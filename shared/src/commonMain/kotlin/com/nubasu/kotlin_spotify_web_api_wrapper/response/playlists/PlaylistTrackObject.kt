package com.nubasu.kotlin_spotify_web_api_wrapper.response.playlists

import com.nubasu.kotlin_spotify_web_api_wrapper.response.player.PlaybackItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistTrackObject(
    @SerialName("added_at")
    val addedAt: String? = null,
    @SerialName("added_by")
    val addedBy: AddedBy? = null,
    @SerialName("is_local")
    val isLocal: Boolean = false,
    @SerialName("item")
    val item: PlaybackItem? = null,
    @Deprecated(
        "Spotify Playlist Item payload now uses `item`. `track` is kept for backward compatibility.",
        ReplaceWith("item")
    )
    @SerialName("track")
    val track: PlaybackItem? = null,
)
