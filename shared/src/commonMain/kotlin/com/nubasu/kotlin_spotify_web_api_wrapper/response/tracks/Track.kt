package com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks

import com.nubasu.kotlin_spotify_web_api_wrapper.response.LinkedFrom
import com.nubasu.kotlin_spotify_web_api_wrapper.response.albums.SimplifiedArtistObject
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalIds
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.ExternalUrls
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.Restrictions

data class Track(
    val album: Album,
    val artists: List<SimplifiedArtistObject>,
    val availableMarkets: List<String>,
    val discNumber: Int,
    val durationMs: Int,
    val explicit: Boolean,
    val externalIds: ExternalIds,
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val isPlayable: Boolean,
    val linkedFrom: LinkedFrom,
    val restrictions: Restrictions,
    val name: String,
    val popularity: Int,
    @Deprecated("")
    val previewUrl: String?,
    val trackNumber: Int,
    val type: String,
    val uri: String,
    val isLocal: Boolean,
)
