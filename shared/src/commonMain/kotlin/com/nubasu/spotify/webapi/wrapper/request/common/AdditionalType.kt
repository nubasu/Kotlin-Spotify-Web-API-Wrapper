package com.nubasu.spotify.webapi.wrapper.request.common

/** Additional playable item types accepted by playlist and player endpoints. */
enum class AdditionalType(val value: String) {
    TRACK("track"),
    EPISODE("episode"),
}
