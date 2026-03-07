package com.nubasu.spotify.webapi.wrapper.api.genres

import com.nubasu.spotify.webapi.wrapper.api.BaseSpotifyApi
import com.nubasu.spotify.webapi.wrapper.api.SpotifyEndpoints
import com.nubasu.spotify.webapi.wrapper.api.SpotifyHttpClientFactory
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.genres.AvailableGenreSeeds
import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder
import com.nubasu.spotify.webapi.wrapper.utils.TokenProvider
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.takeFrom

/**
 * Genre domain API for Spotify Web API.
 *
 * Provides recommendation seed genres used by the recommendations endpoint.
 */
class GenresApis(
    client: HttpClient = SpotifyHttpClientFactory.create(),
    tokenProvider: TokenProvider = TokenHolder,
) : BaseSpotifyApi(client, tokenProvider) {
    /**
     * Gets available genre seeds from Spotify recommendations API.
     *
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify marks GET /v1/recommendations/available-genre-seeds as deprecated.",
    )
    suspend fun getAvailableGenreSeeds(): SpotifyApiResponse<AvailableGenreSeeds> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.GENRES_SEEDS)
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }
}
