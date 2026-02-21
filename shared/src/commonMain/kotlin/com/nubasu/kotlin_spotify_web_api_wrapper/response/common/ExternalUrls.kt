package com.nubasu.spotify.webapi.wrapper.response.common

import kotlinx.serialization.Serializable

@Serializable
data class ExternalUrls(
    val spotify: String? = null,
)
