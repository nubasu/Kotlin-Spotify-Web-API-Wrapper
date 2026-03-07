package com.nubasu.spotify.webapi.wrapper.api

import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder
import com.nubasu.spotify.webapi.wrapper.utils.TokenProvider
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.http.ContentType

/**
 * Base class for Spotify Web API (`api.spotify.com/v1`) domain API classes.
 *
 * Provides a shared [HttpClient] and [TokenProvider], and a [spotifyAuth] helper that
 * sets the `Authorization: Bearer` header and `Accept: application/json` on every request.
 *
 * Note: [AuthorizationApis] targets `accounts.spotify.com` and does not extend this class.
 */
abstract class BaseSpotifyApi(
    protected val client: HttpClient = SpotifyHttpClientFactory.create(),
    protected val tokenProvider: TokenProvider = TokenHolder,
) {
    /**
     * Configures the request with Spotify bearer authentication and JSON accept header.
     *
     * Call this inside every `client.get/put/post/delete { }` block.
     */
    protected fun HttpRequestBuilder.spotifyAuth() {
        bearerAuth(tokenProvider.getToken())
        accept(ContentType.Application.Json)
    }
}
