package com.nubasu.spotify.webapi.wrapper.request.player

import kotlinx.serialization.Serializable

@Serializable
data class State(
    val repeatMode: RepeatMode,
)
