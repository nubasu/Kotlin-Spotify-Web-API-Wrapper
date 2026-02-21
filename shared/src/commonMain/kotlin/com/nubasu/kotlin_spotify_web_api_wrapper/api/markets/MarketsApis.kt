package com.nubasu.kotlin_spotify_web_api_wrapper.api.markets

import com.nubasu.kotlin_spotify_web_api_wrapper.api.toSpotifyApiResponse

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyApiResponse
import com.nubasu.kotlin_spotify_web_api_wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.kotlin_spotify_web_api_wrapper.response.markets.AvailableMarkets
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.TokenHolder
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

class MarketsApis(
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
) {

    suspend fun getAvailableMarkets() : SpotifyApiResponse<AvailableMarkets> {
        val ENDPOINT = "https://api.spotify.com/v1/markets"
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
