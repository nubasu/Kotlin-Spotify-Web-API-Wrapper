package com.nubasu.kotlin_spotify_web_api_wrapper.response.search

data class SearchResponse(
    val tracks: Tracks,
    val artists: Artists,
    val albums: Albums,
    val playlists: Playlists,
    val shows: Shows,
    val episodes: Episodes,
    val audiobooks: Audiobooks,
)
