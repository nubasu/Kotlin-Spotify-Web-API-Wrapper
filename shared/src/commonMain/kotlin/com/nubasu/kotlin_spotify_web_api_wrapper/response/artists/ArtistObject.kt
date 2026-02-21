package com.nubasu.kotlin_spotify_web_api_wrapper.response.artists

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ImageObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// same as Artist
@Serializable
data class ArtistObject(
    @SerialName("external_urls")
    val externalUrls: ExternalUrls? = null,
    val followers: Followers? = null,
    val genres: List<String> = listOf(),
    val href: String? = null,
    val id: String? = null,
    val images: List<ImageObject> = listOf(),
    val name: String? = null,
    val popularity: Int? = null,
    val type: String? = null,
    val uri: String? = null,
)

