package com.nubasu.kotlin_spotify_web_api_wrapper.response.common

import kotlinx.serialization.Serializable

@Serializable
data class CopyrightObject(
    val text: String? = null,
    val type: String? = null,
)
