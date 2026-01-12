package com.nubasu.kotlin_spotify_web_api_wrapper.response.audiobooks

data class UsersSavedAudiobooks(
    val href: String,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int,
    val items: List<SimplifiedAudiobookObject>
)