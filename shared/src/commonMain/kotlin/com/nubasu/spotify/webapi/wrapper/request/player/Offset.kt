package com.nubasu.spotify.webapi.wrapper.request.player

import kotlinx.serialization.Serializable

@Serializable
data class Offset(
    val position: Int? = null,
    val uri: String? = null,
)
