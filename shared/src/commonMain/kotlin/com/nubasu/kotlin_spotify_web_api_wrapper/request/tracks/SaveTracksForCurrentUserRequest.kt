package com.nubasu.spotify.webapi.wrapper.request.tracks

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SaveTracksForCurrentUserRequest(
    val ids: List<String>? = null,
    @SerialName("timestamped_ids")
    val timestampedIds: List<TimestampedTrackId>? = null,
)

@Serializable
data class TimestampedTrackId(
    val id: String,
    @SerialName("added_at")
    val addedAt: String,
)
