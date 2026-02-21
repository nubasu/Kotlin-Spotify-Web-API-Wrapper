package com.nubasu.kotlin_spotify_web_api_wrapper

interface DesktopCallbackCoordinator {
    fun beginSession(): String?
    fun consumeCallbackUri(): String?
    fun close()
}
