package com.nubasu.spotify.webapi.wrapper

interface DesktopCallbackCoordinator {
    fun beginSession(): String?
    fun consumeCallbackUri(): String?
    fun close()
}
