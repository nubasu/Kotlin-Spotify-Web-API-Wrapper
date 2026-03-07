package com.nubasu.spotify.webapi.wrapper.utils

import kotlin.concurrent.Volatile

object TokenHolder : TokenProvider {
    // @JvmField removes the generated getter (getToken()) that would clash with TokenProvider.getToken()
    @JvmField
    @Volatile
    var token: String = ""

    @Volatile
    var tokenProvider: (() -> String?)? = null

    override fun getToken(): String = getTokenFromProvider()

    fun getTokenFromProvider(): String {
        token.trim().takeIf { it.isNotEmpty() }?.let { return it }

        val t =
            tokenProvider?.invoke()?.trim()?.takeIf { it.isNotEmpty() }
                ?: error("Spotify token is missing. Set TokenHolder.token or TokenHolder.tokenProvider.")

        token = t
        return t
    }
}
