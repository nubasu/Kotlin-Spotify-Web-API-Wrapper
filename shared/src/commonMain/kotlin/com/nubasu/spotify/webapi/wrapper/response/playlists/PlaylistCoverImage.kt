package com.nubasu.spotify.webapi.wrapper.response.playlists

import com.nubasu.spotify.webapi.wrapper.response.common.ImageObject
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistCoverImage(
    val images: List<ImageObject>,
)
