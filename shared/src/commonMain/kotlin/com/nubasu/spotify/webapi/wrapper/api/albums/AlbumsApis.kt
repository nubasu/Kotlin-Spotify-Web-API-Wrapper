package com.nubasu.spotify.webapi.wrapper.api.albums

import com.nubasu.spotify.webapi.wrapper.api.BaseSpotifyApi
import com.nubasu.spotify.webapi.wrapper.api.SpotifyEndpoints
import com.nubasu.spotify.webapi.wrapper.api.SpotifyHttpClientFactory
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
import com.nubasu.spotify.webapi.wrapper.utils.TokenProvider
import com.nubasu.spotify.webapi.wrapper.utils.applyLimitOffsetPaging
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom

/**
 * Album domain API for Spotify Web API.
 *
 * Covers album lookup, album tracks, and the current user's saved albums in Your Library.
 */
class AlbumsApis(
    client: HttpClient = SpotifyHttpClientFactory.create(),
    tokenProvider: TokenProvider = TokenHolder,
) : BaseSpotifyApi(client, tokenProvider) {
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
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ALBUMS)
                    appendPathSegments(id)
                    market?.let { parameters.append("market", it.code) }
                }
                spotifyAuth()
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
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ALBUMS)
                    parameters.append("ids", ids.joinToString(","))
                    market?.let { parameters.append("market", it.code) }
                }
                spotifyAuth()
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
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ALBUMS)
                    appendPathSegments(id, "tracks")
                    market?.let { parameters.append("market", it.code) }
                    applyLimitOffsetPaging(pagingOptions)
                }
                spotifyAuth()
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
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ME_ALBUMS)
                    market?.let { parameters.append("market", it.code) }
                    applyLimitOffsetPaging(pagingOptions)
                }
                spotifyAuth()
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
        val response =
            client.put(SpotifyEndpoints.ME_ALBUMS) {
                spotifyAuth()
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
        val response =
            client.delete(SpotifyEndpoints.ME_ALBUMS) {
                spotifyAuth()
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
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ME_ALBUMS_CONTAINS)
                    parameters.append("ids", ids.ids.joinToString(","))
                }
                spotifyAuth()
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
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.NEW_RELEASES)
                    country?.let { parameters.append("country", it.code) }
                    applyLimitOffsetPaging(pagingOptions)
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }
}
