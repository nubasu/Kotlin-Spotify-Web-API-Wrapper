package com.nubasu.kotlin_spotify_web_api_wrapper.response.users

import kotlinx.serialization.Serializable

@Serializable
data class Followers(
    val href: String?,
    val total: Int,
)
