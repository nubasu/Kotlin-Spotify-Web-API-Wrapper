package com.nubasu.kotlin_spotify_web_api_wrapper.response.player

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Context(
    val type: String? = null,
    val href: String? = null,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls? = null,
    val uri: String? = null,
)
