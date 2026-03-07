package com.nubasu.spotify.webapi.wrapper.api.markets

import com.nubasu.spotify.webapi.wrapper.api.BaseSpotifyApi
import com.nubasu.spotify.webapi.wrapper.api.SpotifyEndpoints
import com.nubasu.spotify.webapi.wrapper.api.SpotifyHttpClientFactory
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.markets.AvailableMarkets
import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder
import com.nubasu.spotify.webapi.wrapper.utils.TokenProvider
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.takeFrom

/**
 * Market domain API for Spotify Web API.
 *
 * Provides the list of ISO country codes where Spotify content is available.
 */
class MarketsApis(
    client: HttpClient = SpotifyHttpClientFactory.create(),
    tokenProvider: TokenProvider = TokenHolder,
) : BaseSpotifyApi(client, tokenProvider) {
    /**
     * Gets markets where Spotify content is available.
     *
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify marks GET /v1/markets as deprecated.",
    )
    suspend fun getAvailableMarkets(): SpotifyApiResponse<AvailableMarkets> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.MARKETS)
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }
}
