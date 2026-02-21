package com.nubasu.spotify.webapi.wrapper.api.markets

import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.markets.AvailableMarkets
import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

/**
 * Market domain API for Spotify Web API.
 *
 * Provides the list of ISO country codes where Spotify content is available.
 */
class MarketsApis(
    private val client: HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        },
) {
    /**
     * Gets markets where Spotify content is available.
     *
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify marks GET /v1/markets as deprecated.",
    )
    suspend fun getAvailableMarkets(): SpotifyApiResponse<AvailableMarkets> {
        val endpoint = "https://api.spotify.com/v1/markets"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }
}
