package com.nubasu.kotlin_spotify_web_api_wrapper.response.playlists

import com.nubasu.kotlin_spotify_web_api_wrapper.response.player.PlaybackItem

data class PlaylistTrackObject(
    val addedAt: String,
    val addedBy: AddedBy,
    val isLocal: Boolean,
    val track: PlaybackItem,
)
