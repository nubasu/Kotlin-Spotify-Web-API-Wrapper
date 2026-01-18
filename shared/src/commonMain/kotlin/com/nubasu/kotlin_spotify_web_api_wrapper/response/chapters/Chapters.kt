package com.nubasu.kotlin_spotify_web_api_wrapper.response.chapters

import kotlinx.serialization.Serializable

@Serializable
data class Chapters(
    val chapters: List<Chapter>,
)