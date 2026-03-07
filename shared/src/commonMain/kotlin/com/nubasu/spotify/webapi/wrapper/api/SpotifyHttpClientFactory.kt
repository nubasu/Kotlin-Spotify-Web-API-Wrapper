package com.nubasu.spotify.webapi.wrapper.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

/**
 * Factory for creating default [HttpClient] instances configured for Spotify APIs.
 *
 * Prefer injecting a shared [HttpClient] instance via the constructor of each `*Apis` class
 * rather than relying on the per-instance default, so the client lifecycle is controlled by the caller.
 */
internal object SpotifyHttpClientFactory {
    /** Creates a client for the Spotify Web API (`api.spotify.com`). */
    fun create(): HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        }

    /** Creates a client for the Spotify Accounts service (`accounts.spotify.com`). */
    fun createAccountsClient(): HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        }
}
