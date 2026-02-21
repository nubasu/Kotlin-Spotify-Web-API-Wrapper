package com.nubasu.spotify.webapi.wrapper.api.albums

import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.spotify.webapi.wrapper.request.common.Ids
import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import com.nubasu.spotify.webapi.wrapper.response.albums.Album
import com.nubasu.spotify.webapi.wrapper.response.albums.AlbumTracks
import com.nubasu.spotify.webapi.wrapper.response.albums.Albums
import com.nubasu.spotify.webapi.wrapper.response.albums.NewRelease
import com.nubasu.spotify.webapi.wrapper.response.albums.UsersSavedAlbums
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
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
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

/**
 * Album domain API for Spotify Web API.
 *
 * Covers album lookup, album tracks, and the current user's saved albums in Your Library.
 */
class AlbumsApis(
    private val client: HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        },
) {
    /**
     * Gets a Spotify album by album ID.
     *
     * @param id Spotify ID of the target resource for this endpoint.
     * @param market Market (country) code used to localize and filter content.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getAlbum(
        id: String,
        market: CountryCode? = null,
    ): SpotifyApiResponse<Album> {
        val endpoint = "https://api.spotify.com/v1/albums"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    appendPathSegments(id)
                    market?.let { parameters.append("market", market.code) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets multiple Spotify albums by their IDs.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @param market Market (country) code used to localize and filter content.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify marks GET /v1/albums as deprecated.",
    )
    suspend fun getSeveralAlbums(
        ids: List<String>,
        market: CountryCode? = null,
    ): SpotifyApiResponse<Albums> {
        val endpoint = "https://api.spotify.com/v1/albums"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    parameters.append("ids", ids.joinToString(","))
                    market?.let { parameters.append("market", market.code) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets tracks from a Spotify album.
     *
     * @param id Spotify ID of the target resource for this endpoint.
     * @param market Market (country) code used to localize and filter content.
     * @param pagingOptions Paging options (`limit`, `offset`) used for paged endpoints.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getAlbumTracks(
        id: String,
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ): SpotifyApiResponse<AlbumTracks> {
        val endpoint = "https://api.spotify.com/v1/albums"
        val limit = pagingOptions.limit
        val offset = pagingOptions.offset
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    appendPathSegments(id, "tracks")
                    market?.let { parameters.append("market", it.code) }
                    limit?.let { parameters.append("limit", it.toString()) }
                    offset?.let { parameters.append("offset", it.toString()) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets the current user's saved albums from Your Library.
     *
     * @param market Market (country) code used to localize and filter content.
     * @param pagingOptions Paging options (`limit`, `offset`) used for paged endpoints.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getUsersSavedAlbums(
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ): SpotifyApiResponse<UsersSavedAlbums> {
        val endpoint = "https://api.spotify.com/v1/me/albums"
        val limit = pagingOptions.limit
        val offset = pagingOptions.offset
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    market?.let { parameters.append("market", it.code) }
                    limit?.let { parameters.append("limit", it.toString()) }
                    offset?.let { parameters.append("offset", it.toString()) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Saves albums to the current user's Your Library.
     *
     * @param body Request payload object serialized for this endpoint.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    @Deprecated(
        "Spotify marks PUT /v1/me/albums as deprecated.",
    )
    suspend fun saveAlbumsForCurrentUser(body: Ids): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/albums"
        val response =
            client.put(endpoint) {
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                url {
                    parameters.append("ids", body.ids.joinToString(","))
                }
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Removes albums from the current user's Your Library.
     *
     * @param body Request payload object serialized for this endpoint.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    @Deprecated(
        "Spotify marks DELETE /v1/me/albums as deprecated.",
    )
    suspend fun removeUsersSavedAlbums(body: Ids): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/albums"
        val response =
            client.delete(endpoint) {
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                url {
                    parameters.append("ids", body.ids.joinToString(","))
                }
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Checks whether albums are saved in the current user's Your Library.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @return Wrapped Spotify API response. `data` contains per-item boolean flags from Spotify.
     */
    suspend fun checkUsersSavedAlbums(ids: Ids): SpotifyApiResponse<List<Boolean>> {
        val endpoint = "https://api.spotify.com/v1/me/albums/contains"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    parameters.append("ids", ids.ids.joinToString(","))
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets Spotify new-release albums.
     *
     * @param pagingOptions Paging options (`limit`, `offset`) used for paged endpoints.
     * @param country Country code used for market-specific filtering.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getNewReleases(
        pagingOptions: PagingOptions = PagingOptions(),
        country: CountryCode? = null,
    ): SpotifyApiResponse<NewRelease> {
        val endpoint = "https://api.spotify.com/v1/browse/new-releases"
        val limit = pagingOptions.limit
        val offset = pagingOptions.offset
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    country?.let { parameters.append("country", it.code) }
                    limit?.let { parameters.append("limit", it.toString()) }
                    offset?.let { parameters.append("offset", it.toString()) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }
}
