package com.nubasu.kotlin_spotify_web_api_wrapper.response.users

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ImageObject

data class UsersProfile(
    val displayName: String,
    val externalUrls: ExternalUrls,
    val followers: Followers,
    val href: String,
    val id: String,
    val images: List<ImageObject>,
    val type: String,
    val uri: String,
)
