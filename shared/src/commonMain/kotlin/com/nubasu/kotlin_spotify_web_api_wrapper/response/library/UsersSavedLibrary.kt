package com.nubasu.kotlin_spotify_web_api_wrapper.response.library

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class UsersSavedLibrary(
    val href: String? = null,
    val limit: Int? = null,
    val next: String? = null,
    val offset: Int? = null,
    val previous: String? = null,
    val total: Int? = null,
    val items: List<SavedLibraryItem> = emptyList(),
)

@Serializable
data class SavedLibraryItem(
    @SerialName("added_at")
    val addedAt: String? = null,
    val item: JsonObject? = null,
)
