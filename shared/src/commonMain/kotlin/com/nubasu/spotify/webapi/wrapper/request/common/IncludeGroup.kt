package com.nubasu.spotify.webapi.wrapper.request.common

enum class IncludeGroup(
    val value: String,
) {
    Album("album"),
    Single("single"),
    AppearsOn("appears_on"),
    Compilation("compilation"),
}
