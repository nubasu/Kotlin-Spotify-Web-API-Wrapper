package com.nubasu.kotlin_spotify_web_api_wrapper.response.artists

import kotlinx.serialization.Serializable

@Serializable
data class Followers(
    val href: String? = null,
    val total: Int? = null,
)
