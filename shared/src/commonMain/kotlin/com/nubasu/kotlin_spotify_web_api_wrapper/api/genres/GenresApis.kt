package com.nubasu.kotlin_spotify_web_api_wrapper.api.genres

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyApiResponse

import com.nubasu.kotlin_spotify_web_api_wrapper.response.genres.AvailableGenreSeeds
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.TokenHolder
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

class GenresApis(
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
) {

    @Deprecated("")
    suspend fun getAvailableGenreSeeds() : SpotifyApiResponse<AvailableGenreSeeds> {
        val ENDPOINT = "https://api.spotify.com/v1/recommendations/available-genre-seeds"
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return SpotifyApiResponse(response.status.value, response.body())
    }
}
