package com.nubasu.kotlin_spotify_web_api_wrapper.response.playlists

data class Playlists(
    val href: String,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int,
    val items: List<SimplifiedPlaylistObject>
)
