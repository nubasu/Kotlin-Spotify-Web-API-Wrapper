package com.nubasu.kotlin_spotify_web_api_wrapper.response.common

import kotlinx.serialization.Serializable

@Serializable
data class Restrictions(
    val reason: String,
)
