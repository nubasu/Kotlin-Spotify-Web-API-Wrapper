package com.nubasu.kotlin_spotify_web_api_wrapper.response

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls

data class LinkedFrom(
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val type: String,
    val uri: String,
)
