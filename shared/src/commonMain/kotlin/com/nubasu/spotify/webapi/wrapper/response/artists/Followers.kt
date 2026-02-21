package com.nubasu.spotify.webapi.wrapper.response.artists

import kotlinx.serialization.Serializable

@Serializable
data class Followers(
    val href: String? = null,
    val total: Int? = null,
)
