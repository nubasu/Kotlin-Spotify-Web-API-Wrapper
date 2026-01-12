package com.nubasu.kotlin_spotify_web_api_wrapper.response.player

data class Actions(
    val interruptingPlayback: Boolean,
    val pausing: Boolean,
    val resuming: Boolean,
    val seeking: Boolean,
    val skippingNext: Boolean,
    val skippingPrev: Boolean,
    val togglingRepeatContext: Boolean,
    val togglingShuffle: Boolean,
    val togglingRepeatTrack: Boolean,
    val transferringPlayback: Boolean,
)
