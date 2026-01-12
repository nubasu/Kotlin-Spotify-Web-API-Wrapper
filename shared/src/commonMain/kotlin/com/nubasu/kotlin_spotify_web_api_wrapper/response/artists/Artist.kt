package com.nubasu.kotlin_spotify_web_api_wrapper.response.artists

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ImageObject

// same as ArtistObject
data class Artist(
    val externalUrls: ExternalUrls,
    val followers: Followers,
    val genres: List<String>,
    val href: String,
    val id: String,
    val images: List<ImageObject>,
    val name: String,
    val popularity: Int,
    val type: String,
    val uri: String,
)
