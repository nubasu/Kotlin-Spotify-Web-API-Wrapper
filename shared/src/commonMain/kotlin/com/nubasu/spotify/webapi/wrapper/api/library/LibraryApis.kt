package com.nubasu.spotify.webapi.wrapper.api.library

import com.nubasu.spotify.webapi.wrapper.api.BaseSpotifyApi
import com.nubasu.spotify.webapi.wrapper.api.SpotifyEndpoints
import com.nubasu.spotify.webapi.wrapper.api.SpotifyHttpClientFactory
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.library.UsersSavedLibrary
import com.nubasu.spotify.webapi.wrapper.utils.CountryCode
import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder
import com.nubasu.spotify.webapi.wrapper.utils.TokenProvider
import com.nubasu.spotify.webapi.wrapper.utils.applyLimitOffsetPaging
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.http.takeFrom

/**
 * Library domain API for Spotify Web API.
 *
 * Covers save/remove/check operations in the current user's Your Library collection.
 */
class LibraryApis(
    client: HttpClient = SpotifyHttpClientFactory.create(),
    tokenProvider: TokenProvider = TokenHolder,
) : BaseSpotifyApi(client, tokenProvider) {
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
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ME_LIBRARY)
                    market?.let { parameters.append("market", it.code) }
                    applyLimitOffsetPaging(pagingOptions)
                }
                spotifyAuth()
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
        val response =
            client.put {
                url {
                    takeFrom(SpotifyEndpoints.ME_LIBRARY)
                    parameters.append("uris", uris.joinToString(","))
                }
                spotifyAuth()
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
        val response =
            client.delete {
                url {
                    takeFrom(SpotifyEndpoints.ME_LIBRARY)
                    parameters.append("uris", uris.joinToString(","))
                }
                spotifyAuth()
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
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ME_LIBRARY_CONTAINS)
                    parameters.append("uris", uris.joinToString(","))
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }
}
