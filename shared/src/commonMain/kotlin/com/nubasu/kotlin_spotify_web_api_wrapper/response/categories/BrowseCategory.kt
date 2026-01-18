package com.nubasu.kotlin_spotify_web_api_wrapper.response.categories

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ImageObject
import kotlinx.serialization.Serializable

@Serializable
data class BrowseCategory(
    val href: String,
    val icons: List<ImageObject>,
    val id: String,
    val name: String,
)
