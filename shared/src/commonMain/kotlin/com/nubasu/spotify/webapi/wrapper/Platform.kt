package com.nubasu.spotify.webapi.wrapper

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
