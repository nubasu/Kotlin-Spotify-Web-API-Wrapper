package com.nubasu.kotlin_spotify_web_api_wrapper

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
