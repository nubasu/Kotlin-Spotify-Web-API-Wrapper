package com.nubasu.kotlin_spotify_web_api_wrapper.response.show

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SavedShowObject(
    @SerialName("added_at")
    val addedAt: String,
    val show: SimplifiedShowObject,
)
