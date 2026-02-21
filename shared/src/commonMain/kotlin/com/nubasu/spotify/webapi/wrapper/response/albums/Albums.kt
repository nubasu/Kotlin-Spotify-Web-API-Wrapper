package com.nubasu.spotify.webapi.wrapper.response.albums

import kotlinx.serialization.Serializable

@Serializable
data class Albums(
    val albums: List<Album>
)