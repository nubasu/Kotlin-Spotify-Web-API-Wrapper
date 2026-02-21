package com.nubasu.spotify.webapi.wrapper.api.categories

import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import com.nubasu.spotify.webapi.wrapper.response.categories.BrowseCategories
import com.nubasu.spotify.webapi.wrapper.response.categories.BrowseCategory
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

class CategoriesApis(
    private val client: HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        },
) {
    @Deprecated(
        "Spotify marks GET /v1/browse/categories as deprecated.",
    )
    suspend fun getSeveralBrowseCategories(
        locale: String? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ): SpotifyApiResponse<BrowseCategories> {
        val endpoint = "https://api.spotify.com/v1/browse/categories"
        val limit = pagingOptions.limit
        val offset = pagingOptions.offset
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    locale?.let { parameters.append("locale", locale) }
                    limit?.let { parameters.append("limit", it.toString()) }
                    offset?.let { parameters.append("offset", it.toString()) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    @Deprecated(
        "Spotify marks GET /v1/browse/categories/{category_id} as deprecated.",
    )
    suspend fun getSingleBrowseCategory(
        categoryId: String,
        locale: String? = null,
    ): SpotifyApiResponse<BrowseCategory> {
        val endpoint = "https://api.spotify.com/v1/browse/categories"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    appendPathSegments(categoryId)
                    locale?.let { parameters.append("locale", locale) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }
}
