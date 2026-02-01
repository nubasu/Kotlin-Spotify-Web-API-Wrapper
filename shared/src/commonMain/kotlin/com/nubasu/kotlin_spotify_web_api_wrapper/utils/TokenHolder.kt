package com.nubasu.kotlin_spotify_web_api_wrapper.utils

import kotlin.concurrent.Volatile

object TokenHolder {
    @Volatile
    var token: String = ""

    // for debug
    @Volatile
    var tokenProvider: (() -> String?)? = null

    fun getTokenFromProvider(): String {
        token?.trim()?.takeIf { it.isNotEmpty() }?.let { return it }

        val t = tokenProvider?.invoke()?.trim()?.takeIf { it.isNotEmpty() }
            ?: error("Spotify token is missing. Set TokenHolder.token or TokenHolder.tokenProvider.")

        token = t
        return t
    }
}
