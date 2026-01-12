package com.nubasu.kotlin_spotify_web_api_wrapper.response.player

import com.nubasu.kotlin_spotify_web_api_wrapper.response.player.Actions
import com.nubasu.kotlin_spotify_web_api_wrapper.response.player.PlaybackItem

data class PlaybackState(
    val device: Device,
    val repeatState: String,
    val shuffleState: Boolean,
    val context: Context,
    val timestamp: Int,
    val progressMs: Int,
    val isPlaying: Boolean,
    val item: PlaybackItem,
    val currentlyPlayingType: String,
    val actions: Actions,
)
