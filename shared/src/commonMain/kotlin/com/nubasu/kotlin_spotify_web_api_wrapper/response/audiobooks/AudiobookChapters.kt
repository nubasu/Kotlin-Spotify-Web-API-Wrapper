package com.nubasu.spotify.webapi.wrapper.response.audiobooks

import com.nubasu.spotify.webapi.wrapper.response.chapters.SimplifiedChapterObject
import kotlinx.serialization.Serializable

@Serializable
data class AudiobookChapters(
    val href: String,
    val limit: Int,
    val next: String? = null,
    val offset: Int,
    val previous: String? = null,
    val total: Int,
    val items: List<SimplifiedChapterObject>,
)
