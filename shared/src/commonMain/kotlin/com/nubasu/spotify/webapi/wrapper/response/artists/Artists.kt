package com.nubasu.spotify.webapi.wrapper.response.artists

import kotlinx.serialization.Serializable

@Serializable
data class Artists(
    val artists: List<Artist>,
)
