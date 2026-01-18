package com.nubasu.kotlin_spotify_web_api_wrapper.response.audiobooks

import com.nubasu.kotlin_spotify_web_api_wrapper.response.chapters.SimplifiedChapterObject
import kotlinx.serialization.Serializable

@Serializable
data class Chapters(
    val href: String,
    val limit: Int,
    val next: String? = null,
    val offset: Int,
    val previous: String? = null,
    val total: Int,
    val items: List<SimplifiedChapterObject>,
)
