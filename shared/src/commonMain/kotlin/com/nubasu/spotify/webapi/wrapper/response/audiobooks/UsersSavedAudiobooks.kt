package com.nubasu.spotify.webapi.wrapper.response.audiobooks

import kotlinx.serialization.Serializable

@Serializable
data class UsersSavedAudiobooks(
    val href: String,
    val limit: Int,
    val next: String? = null,
    val offset: Int,
    val previous: String? = null,
    val total: Int,
    val items: List<SimplifiedAudiobookObject>,
)
