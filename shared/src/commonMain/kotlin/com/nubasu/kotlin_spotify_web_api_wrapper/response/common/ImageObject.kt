package com.nubasu.spotify.webapi.wrapper.response.common

import kotlinx.serialization.Serializable

@Serializable
data class ImageObject(
    val url: String,
    val height: Int?,
    val width: Int?,
)