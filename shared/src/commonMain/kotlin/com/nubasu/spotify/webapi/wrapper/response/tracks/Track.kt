package com.nubasu.spotify.webapi.wrapper.response.tracks

import com.nubasu.spotify.webapi.wrapper.response.LinkedFrom
import com.nubasu.spotify.webapi.wrapper.response.albums.SimplifiedArtistObject
import com.nubasu.spotify.webapi.wrapper.response.common.ExternalIds
import com.nubasu.spotify.webapi.wrapper.response.common.ExternalUrls
import com.nubasu.spotify.webapi.wrapper.response.common.Restrictions
import com.nubasu.spotify.webapi.wrapper.response.player.PlaybackItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Track(
    val album: Album? = null,
    val artists: List<SimplifiedArtistObject> = listOf(),
    @SerialName("available_markets")
    val availableMarkets: List<String> = listOf(),
    @SerialName("disc_number")
    val discNumber: Int? = null,
    @SerialName("duration_ms")
    val durationMs: Int? = null,
    val explicit: Boolean? = null,
    @SerialName("external_ids")
    val externalIds: ExternalIds? = null,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls? = null,
    val href: String? = null,
    val id: String? = null,
    @SerialName("is_playable")
    val isPlayable: Boolean? = null,
    @SerialName("linked_from")
    val linkedFrom: LinkedFrom? = null,
    val restrictions: Restrictions? = null,
    val name: String? = null,
    val popularity: Int? = null,
    @Deprecated("")
    @SerialName("preview_url")
    val previewUrl: String? = null,
    @SerialName("track_number")
    val trackNumber: Int? = null,
    val type: String? = null,
    val uri: String? = null,
    @SerialName("is_local")
    val isLocal: Boolean? = null,
) : PlaybackItem
