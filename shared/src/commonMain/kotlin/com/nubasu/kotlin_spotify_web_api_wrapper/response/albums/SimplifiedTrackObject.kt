package com.nubasu.kotlin_spotify_web_api_wrapper.response.albums

import com.nubasu.kotlin_spotify_web_api_wrapper.response.LinkedFrom
import com.nubasu.kotlin_spotify_web_api_wrapper.response.albums.SimplifiedArtistObject
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.Restrictions
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimplifiedTrackObject(
    val artists: List<SimplifiedArtistObject> = emptyList(),
    @SerialName("available_markets")
    val availableMarkets: List<String> = emptyList(),
    @SerialName("disc_number")
    val discNumber: Int? = null,
    @SerialName("duration_ms")
    val durationMs: Int? = null,
    val explicit: Boolean? = null,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls? = null,
    val href: String? = null,
    val id: String? = null,
    @SerialName("is_playable")
    val isPlayable: Boolean? = null,
    @SerialName("linked_from")
    val linkedFrom: LinkedFrom? = null,
    @SerialName("restrictions")
    val restrictions: Restrictions? = null,
    val name: String? = null,
    @SerialName("preview_url")
    val previewUrl: String? = null,
    @SerialName("track_number")
    val trackNumber: Int? = null,
    val type: String? = null,
    val uri: String? = null,
    @SerialName("is_local")
    val isLocal: Boolean = false,
)
