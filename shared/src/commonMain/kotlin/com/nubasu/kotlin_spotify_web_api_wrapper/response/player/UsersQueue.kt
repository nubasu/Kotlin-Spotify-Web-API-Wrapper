package com.nubasu.kotlin_spotify_web_api_wrapper.response.player

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersQueue(
    @SerialName("currently_playing")
    val currentlyPlaying: PlaybackItem,
    val queue: List<PlaybackItem>,
)
