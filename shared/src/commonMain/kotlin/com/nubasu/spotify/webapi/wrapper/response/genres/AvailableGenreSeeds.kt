package com.nubasu.spotify.webapi.wrapper.response.genres

import kotlinx.serialization.Serializable

@Serializable
data class AvailableGenreSeeds(
    val genres: List<String>,
)
