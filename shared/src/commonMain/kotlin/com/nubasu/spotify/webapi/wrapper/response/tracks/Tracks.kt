package com.nubasu.spotify.webapi.wrapper.response.tracks

import kotlinx.serialization.Serializable

@Serializable
data class Tracks(
    val tracks: List<TrackObject>,
)
