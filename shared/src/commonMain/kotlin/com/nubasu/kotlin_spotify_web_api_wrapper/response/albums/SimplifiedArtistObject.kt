package com.nubasu.kotlin_spotify_web_api_wrapper.response.albums

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimplifiedArtistObject(
    @SerialName("external_urls")
    val externalUrls: ExternalUrls? = null,
    val href: String? = null,
    val id: String? = null,
    val name: String? = null,
    val type: String? = null,
    val uri: String? = null,
)
