package com.nubasu.spotify.webapi.wrapper.response.player

import com.nubasu.spotify.webapi.wrapper.response.common.ExternalUrls
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
