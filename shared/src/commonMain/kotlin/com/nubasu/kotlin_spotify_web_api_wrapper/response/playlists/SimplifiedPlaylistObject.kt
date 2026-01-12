package com.nubasu.kotlin_spotify_web_api_wrapper.response.playlists

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ImageObject

data class SimplifiedPlaylistObject(
    val collaborative: Boolean,
    val description: String?,
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<ImageObject>,
    val name: String,
    val owner: Owner,
    val public: Boolean,
    val snapshotId: String,
    val tracks: Tracks,
    val type: String,
    val uri: String,
)
