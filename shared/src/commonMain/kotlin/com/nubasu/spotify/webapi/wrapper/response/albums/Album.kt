package com.nubasu.spotify.webapi.wrapper.response.albums

import com.nubasu.spotify.webapi.wrapper.response.common.CopyrightObject
import com.nubasu.spotify.webapi.wrapper.response.common.ExternalIds
import com.nubasu.spotify.webapi.wrapper.response.common.ExternalUrls
import com.nubasu.spotify.webapi.wrapper.response.common.ImageObject
import com.nubasu.spotify.webapi.wrapper.response.common.Restrictions
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// same as AlbumObject
@Serializable
data class Album(
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
    val tracks: Tracks,
    val copyrights: List<CopyrightObject>,
    @SerialName("external_ids")
    val externalIds: ExternalIds,
    val genres: List<String> = emptyList(),
    val label: String,
    val popularity: Int,
)
