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

/**
 * Library domain API for Spotify Web API.
 *
 * Covers save/remove/check operations in the current user's Your Library collection.
 */
class LibraryApis(
    private val client: HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) { json() }
        },
) {
    /**
     * Gets the current user's saved library entries.
     *
     * @param market Market (country) code used to localize and filter content.
     * @param pagingOptions Paging options (`limit`, `offset`) used for paged endpoints.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
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

    /**
     * Saves items to the current user's library by URI.
     *
     * @param uris Spotify URIs of target resources.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
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

    /**
     * Removes items from the current user's library by URI.
     *
     * @param uris Spotify URIs of target resources.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
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

    /**
     * Checks whether items are saved in the current user's library.
     *
     * @param uris Spotify URIs of target resources.
     * @return Wrapped Spotify API response. `data` contains per-item boolean flags from Spotify.
     */
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
