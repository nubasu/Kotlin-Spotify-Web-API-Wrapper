package com.nubasu.spotify.webapi.wrapper.utils

import kotlin.concurrent.Volatile

object TokenHolder : TokenProvider {
    @Volatile
    var token: String = ""

    @Volatile
    var tokenProvider: (() -> String?)? = null

    override fun provideToken(): String = getTokenFromProvider()

    fun getTokenFromProvider(): String {
        token.trim().takeIf { it.isNotEmpty() }?.let { return it }

        val t =
            tokenProvider?.invoke()?.trim()?.takeIf { it.isNotEmpty() }
                ?: error("Spotify token is missing. Set TokenHolder.token or TokenHolder.tokenProvider.")

        token = t
        return t
    }
}
