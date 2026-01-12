package com.nubasu.kotlin_spotify_web_api_wrapper.response.albums

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls

data class SimplifiedArtistObject(
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val name: String,
    val type: String,
    val uri: String,
)
