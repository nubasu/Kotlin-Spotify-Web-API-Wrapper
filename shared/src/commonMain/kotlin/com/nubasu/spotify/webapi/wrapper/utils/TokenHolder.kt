package com.nubasu.spotify.webapi.wrapper.utils

import kotlin.concurrent.Volatile

object TokenHolder {
    @Volatile
    var token: String = ""

    // for debug
    /**
     * Retrieves data for getTokenFromProvider.
     *
     * @return The resulting string value.
     */
    @Volatile
    var tokenProvider: (() -> String?)? = null

    fun getTokenFromProvider(): String {
        token?.trim()?.takeIf { it.isNotEmpty() }?.let { return it }

        val t =
            tokenProvider?.invoke()?.trim()?.takeIf { it.isNotEmpty() }
                ?: error("Spotify token is missing. Set TokenHolder.token or TokenHolder.tokenProvider.")

        token = t
        return t
    }
}
