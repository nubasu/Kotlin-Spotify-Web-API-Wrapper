package com.nubasu.spotify.webapi.wrapper.response.common

import kotlinx.serialization.Serializable

@Serializable
data class ExternalIds(
    val isrc: String? = null,
    val ean: String? = null,
    val upc: String? = null,
)
