package com.nubasu.kotlin_spotify_web_api_wrapper.response.users

import com.nubasu.kotlin_spotify_web_api_wrapper.response.Cursors
import com.nubasu.kotlin_spotify_web_api_wrapper.response.artists.ArtistObject

data class Artists(
    val href: String,
    val limit: Int,
    val next: String,
    val cursor: Cursors,
    val total: Int,
    val items: List<ArtistObject>,
)
