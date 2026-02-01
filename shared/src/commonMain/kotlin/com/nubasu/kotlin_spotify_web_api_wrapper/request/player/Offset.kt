package com.nubasu.kotlin_spotify_web_api_wrapper.request.player

import kotlinx.serialization.Serializable

@Serializable
data class Offset(
    val position: Int? = null,
    val uri: String? = null,
)
