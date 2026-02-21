package com.nubasu.kotlin_spotify_web_api_wrapper.response.users

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ImageObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val country: String,
    @SerialName("display_name")
    val displayName: String,
    val email: String,
    @SerialName("explicit_content")
    val explicitContent: ExplicitContent,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val followers: Followers,
    val href: String,
    val id: String,
    val images: List<ImageObject>,
    val product: String,
    val type: String,
    val uri: String,
)
