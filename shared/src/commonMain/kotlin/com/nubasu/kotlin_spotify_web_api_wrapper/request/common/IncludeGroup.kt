package com.nubasu.kotlin_spotify_web_api_wrapper.request.common

enum class IncludeGroup(val value: String) {
    Album("album"),
    Single("single"),
    AppearsOn( "appears_on"),
    Compilation("compilation"),
}