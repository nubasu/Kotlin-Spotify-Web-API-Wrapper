package com.nubasu.spotify.webapi.wrapper.response.users

import com.nubasu.spotify.webapi.wrapper.response.common.ExternalUrls
import com.nubasu.spotify.webapi.wrapper.response.common.ImageObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersProfile(
    @SerialName("display_name")
    val displayName: String,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val followers: Followers,
    val href: String,
    val id: String,
    val images: List<ImageObject>,
    val type: String,
    val uri: String,
)
