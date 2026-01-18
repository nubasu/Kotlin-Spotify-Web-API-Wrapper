package com.nubasu.kotlin_spotify_web_api_wrapper.response.artists

import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.TrackObject
import kotlinx.serialization.Serializable

@Serializable
data class ArtistsTopTracks(
    val tracks: List<TrackObject>,
)
