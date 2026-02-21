package com.nubasu.kotlin_spotify_web_api_wrapper.response.search

import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val tracks: Tracks? = null,
    val artists: Artists? = null,
    val albums: Albums? = null,
    val playlists: Playlists? = null,
    val shows: Shows? = null,
    val episodes: Episodes? = null,
    val audiobooks: Audiobooks? = null,
)
