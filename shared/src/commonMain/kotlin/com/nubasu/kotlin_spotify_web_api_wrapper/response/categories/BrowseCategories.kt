package com.nubasu.kotlin_spotify_web_api_wrapper.response.categories

import kotlinx.serialization.Serializable

@Serializable
data class BrowseCategories(
    val categories: Categories,
)
