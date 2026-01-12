package com.nubasu.kotlin_spotify_web_api_wrapper.response.player

import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.TrackObject

data class UsersQueue(
    val currentlyPlaying: PlaybackItem,
    val queue: List<PlaybackItem>,
)
