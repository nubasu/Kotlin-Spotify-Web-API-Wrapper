package com.nubasu.spotify.webapi.wrapper.request.users

enum class TopItemType(val value: String) {
    ARTISTS("artists"),
    TRACKS("tracks"),
}

enum class TimeRange(val value: String) {
    LONG_TERM("long_term"),
    MEDIUM_TERM("medium_term"),
    SHORT_TERM("short_term"),
}

enum class FollowType(val value: String) {
    ARTIST("artist"),
    USER("user"),
}
