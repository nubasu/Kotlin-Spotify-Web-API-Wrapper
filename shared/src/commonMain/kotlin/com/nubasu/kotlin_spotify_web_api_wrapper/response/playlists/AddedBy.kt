package com.nubasu.kotlin_spotify_web_api_wrapper.response.playlists

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddedBy(
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val type: String,
    val uri: String,
)
