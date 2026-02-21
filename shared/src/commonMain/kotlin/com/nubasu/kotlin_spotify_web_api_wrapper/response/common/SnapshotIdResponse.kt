package com.nubasu.kotlin_spotify_web_api_wrapper.response.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SnapshotIdResponse(
    @SerialName("snapshot_id")
    val snapshotId: String,
)
