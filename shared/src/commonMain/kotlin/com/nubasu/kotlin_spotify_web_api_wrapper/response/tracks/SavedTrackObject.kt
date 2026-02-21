package com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SavedTrackObject(
    @SerialName("added_at")
    val addedAt: String,
    val track: Track,
)
