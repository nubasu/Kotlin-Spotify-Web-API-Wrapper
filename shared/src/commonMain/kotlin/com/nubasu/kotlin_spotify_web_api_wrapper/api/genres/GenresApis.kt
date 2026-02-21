package com.nubasu.spotify.webapi.wrapper.api.genres

import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse

import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.spotify.webapi.wrapper.response.genres.AvailableGenreSeeds
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

class GenresApis(
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
) {

    @Deprecated(
        "Spotify marks GET /v1/recommendations/available-genre-seeds as deprecated.",
    )
    suspend fun getAvailableGenreSeeds() : SpotifyApiResponse<AvailableGenreSeeds> {
        val ENDPOINT = "https://api.spotify.com/v1/recommendations/available-genre-seeds"
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        return response.toSpotifyApiResponse()
    }
}
