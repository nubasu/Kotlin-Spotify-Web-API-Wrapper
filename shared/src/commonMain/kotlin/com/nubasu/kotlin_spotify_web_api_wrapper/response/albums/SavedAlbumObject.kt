package com.nubasu.kotlin_spotify_web_api_wrapper.response.albums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SavedAlbumObject(
    @SerialName("added_at")
    val addedAt: String? = null,
    val album: Album,
)
