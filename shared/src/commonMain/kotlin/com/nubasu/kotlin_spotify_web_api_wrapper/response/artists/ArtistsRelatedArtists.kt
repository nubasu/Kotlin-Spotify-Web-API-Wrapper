package com.nubasu.kotlin_spotify_web_api_wrapper.response.artists

import kotlinx.serialization.Serializable

@Serializable
data class ArtistsRelatedArtists(
    val artists: List<Artist>,
)
