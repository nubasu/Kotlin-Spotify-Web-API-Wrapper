package com.nubasu.kotlin_spotify_web_api_wrapper.api.search

import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.PagingOptions
import com.nubasu.kotlin_spotify_web_api_wrapper.response.search.SearchResponse
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.CountryCode
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

class SearchApis {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun searchForItem(
        query: String,
        types: List<String>,
        market: CountryCode? = null,
        includeExternalAudio: Boolean = false,
        pagingOptions: PagingOptions = PagingOptions(),
    ): SearchResponse {
        val endpoint = "https://api.spotify.com/v1/search"
        val response = client.get {
            url {
                takeFrom(endpoint)
                parameters.append("q", query)
                parameters.append("type", types.joinToString(","))
                market?.let { parameters.append("market", it.code) }
                if (includeExternalAudio) {
                    parameters.append("include_external", "audio")
                }
                pagingOptions.limit?.let { parameters.append("limit", it.toString()) }
                pagingOptions.offset?.let { parameters.append("offset", it.toString()) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }
}
