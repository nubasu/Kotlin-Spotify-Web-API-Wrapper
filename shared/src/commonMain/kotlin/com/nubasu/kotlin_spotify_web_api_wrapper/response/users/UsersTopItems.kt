package com.nubasu.kotlin_spotify_web_api_wrapper.response.users

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class UsersTopItems(
    val href: String,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int,
    val items: List<JsonElement>,
)
