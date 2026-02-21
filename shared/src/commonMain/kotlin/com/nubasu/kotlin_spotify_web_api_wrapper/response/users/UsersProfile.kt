package com.nubasu.kotlin_spotify_web_api_wrapper.response.users

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ImageObject
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
