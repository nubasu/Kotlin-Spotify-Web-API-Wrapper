package com.nubasu.spotify.webapi.wrapper.response.chapters

import kotlinx.serialization.Serializable

@Serializable
data class Chapters(
    val chapters: List<Chapter>,
)