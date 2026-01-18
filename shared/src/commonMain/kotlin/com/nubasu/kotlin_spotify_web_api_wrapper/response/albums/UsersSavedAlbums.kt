package com.nubasu.kotlin_spotify_web_api_wrapper.response.albums

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class UsersSavedAlbums(
    val href: String,
    val limit: Int,
    val next: String? = null,
    val offset: Int,
    val previous: String? = null,
    val total: Int,
    @Contextual val items: List<SavedAlbumObject>
)