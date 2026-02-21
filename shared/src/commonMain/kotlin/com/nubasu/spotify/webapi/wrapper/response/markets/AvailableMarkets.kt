package com.nubasu.spotify.webapi.wrapper.response.markets

import kotlinx.serialization.Serializable

@Serializable
data class AvailableMarkets(
    val markets: List<String>,
)
