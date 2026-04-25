package com.nubasu.spotify.webapi.wrapper.request.common

import kotlinx.serialization.Serializable

@Serializable
data class Uris(
    val uris: List<String>,
) {
    init {
        require(uris.isNotEmpty()) { "uris must not be empty" }
    }
}
