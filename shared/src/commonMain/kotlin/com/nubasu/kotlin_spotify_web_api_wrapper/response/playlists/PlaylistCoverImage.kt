package com.nubasu.kotlin_spotify_web_api_wrapper.response.playlists

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ImageObject
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistCoverImage(
    val images: List<ImageObject>
)
