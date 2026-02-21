package com.nubasu.kotlin_spotify_web_api_wrapper.request.playlists

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangePlaylistDetailsRequest(
    val name: String? = null,
    val public: Boolean? = null,
    val collaborative: Boolean? = null,
    val description: String? = null,
)

@Serializable
data class UpdatePlaylistItemsRequest(
    val uris: List<String>? = null,
    @SerialName("range_start")
    val rangeStart: Int? = null,
    @SerialName("insert_before")
    val insertBefore: Int? = null,
    @SerialName("range_length")
    val rangeLength: Int? = null,
    @SerialName("snapshot_id")
    val snapshotId: String? = null,
)

@Serializable
data class AddItemsToPlaylistRequest(
    val uris: List<String>,
    val position: Int? = null,
)

@Serializable
data class RemovePlaylistItemsRequest(
    val tracks: List<TrackUri>,
    @SerialName("snapshot_id")
    val snapshotId: String? = null,
)

@Serializable
data class TrackUri(
    val uri: String,
)

@Serializable
data class CreatePlaylistRequest(
    val name: String,
    val public: Boolean? = null,
    val collaborative: Boolean? = null,
    val description: String? = null,
)
