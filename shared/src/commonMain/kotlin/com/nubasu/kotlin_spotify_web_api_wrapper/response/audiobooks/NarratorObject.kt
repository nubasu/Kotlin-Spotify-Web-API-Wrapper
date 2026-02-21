package com.nubasu.spotify.webapi.wrapper.response.audiobooks

import kotlinx.serialization.Serializable

@Serializable
data class NarratorObject(
    val name: String? = null,
)
