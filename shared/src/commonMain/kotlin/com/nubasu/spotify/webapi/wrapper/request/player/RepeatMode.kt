package com.nubasu.spotify.webapi.wrapper.request.player

enum class RepeatMode(
    val value: String,
) {
    TRACK("track"),
    CONTEXT("context"),
    OFF("off"),
}
