package com.nubasu.kotlin_spotify_web_api_wrapper.request.common

import com.nubasu.kotlin_spotify_web_api_wrapper.utils.CountryCode

data class MarketOptions(
    val market: CountryCode? = null,
)
