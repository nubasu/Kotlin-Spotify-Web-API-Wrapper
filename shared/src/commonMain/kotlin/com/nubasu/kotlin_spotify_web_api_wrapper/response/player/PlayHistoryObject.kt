package com.nubasu.kotlin_spotify_web_api_wrapper.response.player

import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.Track

data class PlayHistoryObject(
    val track: Track,
    val playedAt: String,
    val context: Context,
)
