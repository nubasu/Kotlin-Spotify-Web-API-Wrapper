package com.nubasu.kotlin_spotify_web_api_wrapper

class JsPlatform : Platform {
    override val name: String = "Web with Kotlin/JS"
}

actual fun getPlatform(): Platform = JsPlatform()