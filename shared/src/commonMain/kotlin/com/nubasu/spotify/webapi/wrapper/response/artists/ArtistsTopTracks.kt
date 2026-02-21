package com.nubasu.spotify.webapi.wrapper.response.artists

import com.nubasu.spotify.webapi.wrapper.response.tracks.TrackObject
import kotlinx.serialization.Serializable

@Serializable
data class ArtistsTopTracks(
    val tracks: List<TrackObject>,
)
