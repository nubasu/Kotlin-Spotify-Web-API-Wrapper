package com.nubasu.kotlin_spotify_web_api_wrapper.request.search

enum class SearchType(val value: String) {
    ALBUM("album"),
    ARTIST("artist"),
    PLAYLIST("playlist"),
    TRACK("track"),
    SHOW("show"),
    EPISODE("episode"),
    AUDIOBOOK("audiobook")
}
