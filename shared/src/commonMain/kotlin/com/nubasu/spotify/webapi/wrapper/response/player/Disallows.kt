package com.nubasu.spotify.webapi.wrapper.response.player

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Disallows(
    @SerialName("interrupting_playback")
    val interruptingPlayback: Boolean? = null,
    val pausing: Boolean? = null,
    val resuming: Boolean? = null,
    val seeking: Boolean? = null,
    @SerialName("skipping_next")
    val skippingNext: Boolean? = null,
    @SerialName("skipping_prev")
    val skippingPrev: Boolean? = null,
    @SerialName("toggling_repeat_context")
    val togglingRepeatContext: Boolean? = null,
    @SerialName("toggling_shuffle")
    val togglingShuffle: Boolean? = null,
    @SerialName("toggling_repeat_track")
    val togglingRepeatTrack: Boolean? = null,
    @SerialName("transferring_playback")
    val transferringPlayback: Boolean? = null,
)
