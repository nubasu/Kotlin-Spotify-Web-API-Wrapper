package com.nubasu.kotlin_spotify_web_api_wrapper.response

import kotlinx.serialization.Serializable

@Serializable
data class Cursors(
    val after: String,
    val before: String,
)
