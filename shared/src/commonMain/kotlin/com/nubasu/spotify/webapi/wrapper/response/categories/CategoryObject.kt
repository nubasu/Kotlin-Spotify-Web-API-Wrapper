package com.nubasu.spotify.webapi.wrapper.response.categories

import com.nubasu.spotify.webapi.wrapper.response.common.ImageObject
import kotlinx.serialization.Serializable

@Serializable
data class CategoryObject(
    val href: String,
    val icons: List<ImageObject>,
    val id: String,
    val name: String,
)
