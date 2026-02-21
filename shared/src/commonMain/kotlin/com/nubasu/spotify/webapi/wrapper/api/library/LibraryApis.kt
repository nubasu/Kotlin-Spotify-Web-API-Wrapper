package com.nubasu.spotify.webapi.wrapper.api.library

import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.library.UsersSavedLibrary
import com.nubasu.spotify.webapi.wrapper.utils.CountryCode
import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.http.ContentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

class LibraryApis(
    private val client: HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) { json() }
        },
) {
    suspend fun getUsersSavedLibrary(
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ): SpotifyApiResponse<UsersSavedLibrary> {
        val endpoint = "https://api.spotify.com/v1/me/library"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    market?.let { parameters.append("market", it.code) }
                    pagingOptions.limit?.let { parameters.append("limit", it.toString()) }
                    pagingOptions.offset?.let { parameters.append("offset", it.toString()) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    suspend fun saveItemsForCurrentUser(uris: List<String>): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/library"
        val response =
            client.put {
                url {
                    takeFrom(endpoint)
                    parameters.append("uris", uris.joinToString(","))
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyBooleanApiResponse()
    }

    suspend fun removeUsersSavedItems(uris: List<String>): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/library"
        val response =
            client.delete {
                url {
                    takeFrom(endpoint)
                    parameters.append("uris", uris.joinToString(","))
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyBooleanApiResponse()
    }

    suspend fun checkUsersSavedItems(uris: List<String>): SpotifyApiResponse<List<Boolean>> {
        val endpoint = "https://api.spotify.com/v1/me/library/contains"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    parameters.append("uris", uris.joinToString(","))
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }
}
