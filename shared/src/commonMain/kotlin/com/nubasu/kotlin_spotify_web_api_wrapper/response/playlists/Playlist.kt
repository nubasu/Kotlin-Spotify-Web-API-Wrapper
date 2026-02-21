package com.nubasu.kotlin_spotify_web_api_wrapper.response.playlists

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ImageObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Playlist(
    val collaborative: Boolean,
    val description: String?,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<ImageObject>?,
    val name: String,
    val owner: Owner,
    @SerialName("primary_color")
    val primaryColor: String? = null,
    val public: Boolean,
    @SerialName("snapshot_id")
    val snapshotId: String,
    val tracks: Tracks = Tracks(href = "", total = 0),
    @SerialName("items")
    val items: Tracks? = null,
    val type: String,
    val uri: String,
)
