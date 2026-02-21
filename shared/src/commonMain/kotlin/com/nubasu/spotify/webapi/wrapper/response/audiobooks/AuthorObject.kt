package com.nubasu.spotify.webapi.wrapper.response.audiobooks

import kotlinx.serialization.Serializable

@Serializable
data class AuthorObject(
    val name: String? = null,
)
