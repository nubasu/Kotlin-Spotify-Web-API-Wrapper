package com.nubasu.spotify.webapi.wrapper.response.albums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SavedAlbumObject(
    @SerialName("added_at")
    val addedAt: String? = null,
    val album: Album,
)
