package com.nubasu.kotlin_spotify_web_api_wrapper.response.common

import kotlinx.serialization.Serializable

@Serializable
data class ImageObject(
    val url: String,
    val height: Int?,
    val width: Int?,
)