package com.nubasu.spotify.webapi.wrapper.response.artists

import kotlinx.serialization.Serializable

@Serializable
data class ArtistsRelatedArtists(
    val artists: List<Artist>,
)
