package com.nubasu.kotlin_spotify_web_api_wrapper.response.player

import com.nubasu.kotlin_spotify_web_api_wrapper.response.Cursors

data class RecentlyPlayedTracks(
    val href: String,
    val limit: Int,
    val next: String,
    val cursors: Cursors,
    val total: Int,
    val items: List<PlayHistoryObject>
)
