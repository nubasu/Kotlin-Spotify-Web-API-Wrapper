package com.nubasu.kotlin_spotify_web_api_wrapper.response.player

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls

data class Context(
    val type: String,
    val href: String,
    val externalUrls: ExternalUrls,
    val uri: String,
)
