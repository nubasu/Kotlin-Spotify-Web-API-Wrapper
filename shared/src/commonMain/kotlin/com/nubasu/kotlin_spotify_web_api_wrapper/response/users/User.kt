package com.nubasu.kotlin_spotify_web_api_wrapper.response.users

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ImageObject

data class User(
    val country: String,
    val displayName: String,
    val email: String,
    val explicitContent: ExplicitContent,
    val externalUrls: ExternalUrls,
    val followers: Followers,
    val href: String,
    val id: String,
    val images: List<ImageObject>,
    val product: String,
    val type: String,
    val uri: String,
)
