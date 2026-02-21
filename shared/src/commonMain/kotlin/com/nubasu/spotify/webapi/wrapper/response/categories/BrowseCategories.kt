package com.nubasu.spotify.webapi.wrapper.response.categories

import kotlinx.serialization.Serializable

@Serializable
data class BrowseCategories(
    val categories: Categories,
)
