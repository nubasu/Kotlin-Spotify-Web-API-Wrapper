package com.nubasu.spotify.webapi.wrapper.response.users

import com.nubasu.spotify.webapi.wrapper.response.common.ExternalUrls
import com.nubasu.spotify.webapi.wrapper.response.common.ImageObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val country: String? = null,
    @SerialName("display_name")
    val displayName: String? = null,
    val email: String? = null,
    @SerialName("explicit_content")
    val explicitContent: ExplicitContent? = null,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val followers: Followers? = null,
    val href: String,
    val id: String,
    val images: List<ImageObject> = emptyList(),
    val product: String? = null,
    val type: String,
    val uri: String,
)
