package com.nubasu.spotify.webapi.wrapper.response.audiobooks

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResumePoint(
    @SerialName("fully_played")
    val fullyPlayed: Boolean? = null,
    @SerialName("resume_position_ms")
    val resumePositionMs: Int? = null,
)
