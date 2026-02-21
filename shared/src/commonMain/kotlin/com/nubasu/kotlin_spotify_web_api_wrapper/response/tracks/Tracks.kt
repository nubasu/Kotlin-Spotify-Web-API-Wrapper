package com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks

import kotlinx.serialization.Serializable

@Serializable
data class Tracks(
    val tracks: List<TrackObject>,
)
