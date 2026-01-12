package com.nubasu.kotlin_spotify_web_api_wrapper.response.albums

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.CopyrightObject
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalIds
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ImageObject
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.Restrictions
import com.nubasu.kotlin_spotify_web_api_wrapper.response.albums.SimplifiedArtistObject

// same as AlbumObject
data class Album(
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
    val restrictions: Restrictions?,
    val type: String,
    val uri: String,
    val artists: List<SimplifiedArtistObject>,
    val tracks: Tracks,
    val copyrights: List<CopyrightObject>,
    val externalIds: ExternalIds,
    @Deprecated("The array is always empty")
    val genres: List<String>,
    val label: String,
    val popularity: Int,
)
