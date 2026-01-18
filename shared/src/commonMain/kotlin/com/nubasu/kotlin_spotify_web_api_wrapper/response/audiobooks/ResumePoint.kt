package com.nubasu.kotlin_spotify_web_api_wrapper.response.audiobooks

import kotlinx.serialization.Serializable

@Serializable
data class ResumePoint(
    val fullyPlayed: Boolean? = null,
    val resumePositonMs: Int? = null,
)
