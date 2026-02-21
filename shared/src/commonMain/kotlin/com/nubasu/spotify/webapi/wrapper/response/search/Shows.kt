package com.nubasu.spotify.webapi.wrapper.response.search

import com.nubasu.spotify.webapi.wrapper.response.show.SimplifiedShowObject
import kotlinx.serialization.Serializable

@Serializable
data class Shows(
    val href: String,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int,
    val items: List<SimplifiedShowObject>,
)
