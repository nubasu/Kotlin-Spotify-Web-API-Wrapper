package com.nubasu.kotlin_spotify_web_api_wrapper.api.categories

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyApiResponse

import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.PagingOptions
import com.nubasu.kotlin_spotify_web_api_wrapper.response.categories.BrowseCategories
import com.nubasu.kotlin_spotify_web_api_wrapper.response.categories.BrowseCategory
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
import io.ktor.http.appendPathSegments
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

class CategoriesApis(
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
) {

    suspend fun getSeveralBrowseCategories(
        locale: String? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ) : SpotifyApiResponse<BrowseCategories> {
        val ENDPOINT = "https://api.spotify.com/v1/browse/categories"
        val limit = pagingOptions.limit
        val offset = pagingOptions.offset
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                locale?.let { parameters.append("locale", locale) }
                limit?.let { parameters.append("limit", it.toString()) }
                offset?.let { parameters.append("offset", it.toString()) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return SpotifyApiResponse(response.status.value, response.body())
    }

    suspend fun getSingleBrowseCategory(
        categoryId: String,
        locale: String? = null,
    ) : SpotifyApiResponse<BrowseCategory> {
        val ENDPOINT = "https://api.spotify.com/v1/browse/categories"
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                appendPathSegments(categoryId)
                locale?.let { parameters.append("locale", locale) }
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
