package com.nubasu.spotify.webapi.wrapper.request.common

import com.nubasu.spotify.webapi.wrapper.utils.CountryCode

data class MarketOptions(
    val market: CountryCode? = null,
)
