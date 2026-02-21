package com.nubasu.spotify.webapi.wrapper.response.playlists

import com.nubasu.spotify.webapi.wrapper.response.common.ExternalUrls
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Owner(
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val type: String,
    val uri: String,
    @SerialName("display_name")
    val displayName: String?,
)
