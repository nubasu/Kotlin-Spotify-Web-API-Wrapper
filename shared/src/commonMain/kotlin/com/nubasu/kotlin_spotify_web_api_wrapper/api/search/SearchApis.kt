package com.nubasu.kotlin_spotify_web_api_wrapper.api.search

import com.nubasu.kotlin_spotify_web_api_wrapper.api.toSpotifyApiResponse

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyApiResponse
import com.nubasu.kotlin_spotify_web_api_wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.PagingOptions
import com.nubasu.kotlin_spotify_web_api_wrapper.request.search.SearchType
import com.nubasu.kotlin_spotify_web_api_wrapper.response.search.SearchResponse
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.CountryCode
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

class SearchApis(
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) { json() }
    }
) {

    suspend fun searchForItem(
        q: String,
        types: Set<SearchType>,
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
        includeExternalAudio: Boolean = false,
    ) : SpotifyApiResponse<SearchResponse> {
        require(types.isNotEmpty()) { "types must not be empty" }
        val endpoint = "https://api.spotify.com/v1/search"
        val response = client.get {
            url {
                takeFrom(endpoint)
                parameters.append("q", q)
                parameters.append("type", types.joinToString(",") { it.value })
                market?.let { parameters.append("market", it.code) }
                pagingOptions.limit?.let { parameters.append("limit", it.toString()) }
                pagingOptions.offset?.let { parameters.append("offset", it.toString()) }
                if (includeExternalAudio) {
                    parameters.append("include_external", "audio")
                }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
return response.toSpotifyApiResponse()
    }
}
