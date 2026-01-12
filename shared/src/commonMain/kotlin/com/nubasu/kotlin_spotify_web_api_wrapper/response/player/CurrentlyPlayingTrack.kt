package com.nubasu.kotlin_spotify_web_api_wrapper.response.player

data class CurrentlyPlayingTrack(
    val device: Device,
    val repeatState: String,
    val shuffleState: String,
    val context: Context,
    val timestamp: Int,
    val progressMs: Int,
    val isPlaying: Boolean,
    val item: PlaybackItem,
    val currentlyPlayingType: String,
    val actions: Actions
)
