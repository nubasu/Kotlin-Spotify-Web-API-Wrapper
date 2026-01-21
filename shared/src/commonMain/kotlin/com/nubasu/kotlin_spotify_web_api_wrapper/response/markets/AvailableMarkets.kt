package com.nubasu.kotlin_spotify_web_api_wrapper.response.markets

import kotlinx.serialization.Serializable

@Serializable
data class AvailableMarkets(
    val markets: List<String>,
)
