package com.nubasu.kotlin_spotify_web_api_wrapper.response.genres

import kotlinx.serialization.Serializable

@Serializable
data class AvailableGenreSeeds(
    val genres: List<String>,
)
