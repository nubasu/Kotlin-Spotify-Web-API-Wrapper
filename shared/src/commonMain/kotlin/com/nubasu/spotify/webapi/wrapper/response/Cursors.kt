package com.nubasu.spotify.webapi.wrapper.response

import kotlinx.serialization.Serializable

@Serializable
data class Cursors(
    val after: String,
    val before: String,
)
