package com.nubasu.kotlin_spotify_web_api_wrapper.response.audiobooks

import com.nubasu.kotlin_spotify_web_api_wrapper.response.chapters.SimplifiedChapterObject

data class AudiobookChapters(
    val href: String,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int,
    val items: List<SimplifiedChapterObject>,
)
