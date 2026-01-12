package com.nubasu.kotlin_spotify_web_api_wrapper.response.artists

import com.nubasu.kotlin_spotify_web_api_wrapper.response.albums.SimplifiedArtistObject
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ImageObject
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.Restrictions

data class SimplifiedAlbumObject(
    val albumType: String,
    val totalTracks: Int,
    val availableMarkets: List<String>,
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<ImageObject>,
    val name: String,
    val releaseDate: String,
    val releaseDatePrecision: String,
    val restrictions: Restrictions,
    val type: String,
    val uri: String,
    val artists: List<SimplifiedArtistObject>,
    val albumGroup: String,
)
