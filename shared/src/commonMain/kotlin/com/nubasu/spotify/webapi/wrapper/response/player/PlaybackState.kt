package com.nubasu.spotify.webapi.wrapper.response.player

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaybackState(
    val device: Device? = null,
    @SerialName("repeat_state")
    val repeatState: String? = null,
    @SerialName("shuffle_state")
    val shuffleState: Boolean? = null,
    @SerialName("smart_shuffle")
    val smartShuffle: Boolean? = null,
    val context: Context? = null,
    val timestamp: Long? = null,
    @SerialName("progress_ms")
    val progressMs: Int? = null,
    @SerialName("is_playing")
    val isPlaying: Boolean? = null,
    val item: PlaybackItem? = null,
    @SerialName("currently_playing_type")
    val currentlyPlayingType: String? = null,
    val actions: Actions? = null,
)
