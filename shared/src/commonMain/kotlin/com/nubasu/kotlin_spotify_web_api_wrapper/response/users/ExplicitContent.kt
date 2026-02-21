package com.nubasu.kotlin_spotify_web_api_wrapper.response.users

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExplicitContent(
    @SerialName("filter_enabled")
    val filterEnabled: Boolean,
    @SerialName("filter_locked")
    val filterLocked: Boolean,
)
