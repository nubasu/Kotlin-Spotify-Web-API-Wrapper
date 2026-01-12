package com.nubasu.kotlin_spotify_web_api_wrapper.response.artists

import com.nubasu.kotlin_spotify_web_api_wrapper.response.albums.SimplifiedArtistObject
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ImageObject
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.Restrictions
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimplifiedAlbumObject(
    @SerialName("album_type")
    val albumType: String,
    @SerialName("total_tracks")
    val totalTracks: Int,
    @SerialName("available_markets")
    val availableMarkets: List<String>,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<ImageObject>,
    val name: String,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("release_date_precision")
    val releaseDatePrecision: String,
    val restrictions: Restrictions? = null,
    val type: String,
    val uri: String,
    val artists: List<SimplifiedArtistObject>,
)
