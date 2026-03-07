package com.nubasu.spotify.webapi.wrapper.utils

/**
 * Provides a bearer token for Spotify Web API requests.
 *
 * Implement this interface to supply a custom token source (e.g. auto-refresh via [SpotifyAuthManager]).
 * The default implementation is [TokenHolder].
 */
fun interface TokenProvider {
    fun getToken(): String
}
