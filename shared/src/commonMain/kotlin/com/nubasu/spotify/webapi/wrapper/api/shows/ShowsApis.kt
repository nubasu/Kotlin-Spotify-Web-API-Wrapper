package com.nubasu.spotify.webapi.wrapper.api.shows

import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.spotify.webapi.wrapper.request.common.Ids
import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.show.Show
import com.nubasu.spotify.webapi.wrapper.response.show.ShowEpisodes
import com.nubasu.spotify.webapi.wrapper.response.show.Shows
import com.nubasu.spotify.webapi.wrapper.response.show.UsersSavedShows
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
 * Show domain API for Spotify Web API.
 *
 * Covers podcast show metadata, show episodes, and saved shows for the current user.
 */
class ShowsApis(
    private val client: HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) { json() }
        },
) {
    /**
     * Gets a Spotify show by show ID.
     *
     * @param id Spotify ID of the target resource for this endpoint.
     * @param market Market (country) code used to localize and filter content.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getShow(
        id: String,
        market: CountryCode? = null,
    ): SpotifyApiResponse<Show> {
        val endpoint = "https://api.spotify.com/v1/shows"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    appendPathSegments(id)
                    market?.let { parameters.append("market", it.code) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets multiple Spotify shows by their IDs.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @param market Market (country) code used to localize and filter content.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify marks GET /v1/shows as deprecated.",
    )
    suspend fun getSeveralShows(
        ids: List<String>,
        market: CountryCode? = null,
    ): SpotifyApiResponse<Shows> {
        val endpoint = "https://api.spotify.com/v1/shows"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    parameters.append("ids", ids.joinToString(","))
                    market?.let { parameters.append("market", it.code) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets episodes for a Spotify show.
     *
     * @param id Spotify ID of the target resource for this endpoint.
     * @param market Market (country) code used to localize and filter content.
     * @param pagingOptions Paging options (`limit`, `offset`) used for paged endpoints.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getShowEpisodes(
        id: String,
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ): SpotifyApiResponse<ShowEpisodes> {
        val endpoint = "https://api.spotify.com/v1/shows"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    appendPathSegments(id, "episodes")
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
     * Gets the current user's saved shows from Your Library.
     *
     * @param pagingOptions Paging options (`limit`, `offset`) used for paged endpoints.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getUsersSavedShows(pagingOptions: PagingOptions = PagingOptions()): SpotifyApiResponse<UsersSavedShows> {
        val endpoint = "https://api.spotify.com/v1/me/shows"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    pagingOptions.limit?.let { parameters.append("limit", it.toString()) }
                    pagingOptions.offset?.let { parameters.append("offset", it.toString()) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Saves shows to the current user's Your Library.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    @Deprecated(
        "Spotify marks PUT /v1/me/shows as deprecated.",
    )
    suspend fun saveShowsForCurrentUser(ids: Ids): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/shows"
        val response =
            client.put {
                url {
                    takeFrom(endpoint)
                    parameters.append("ids", ids.ids.joinToString(","))
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Removes shows from the current user's Your Library.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @param market Market (country) code used to localize and filter content.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    @Deprecated(
        "Spotify marks DELETE /v1/me/shows as deprecated.",
    )
    suspend fun removeUsersSavedShows(
        ids: Ids,
        market: CountryCode? = null,
    ): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/shows"
        val response =
            client.delete {
                url {
                    takeFrom(endpoint)
                    parameters.append("ids", ids.ids.joinToString(","))
                    market?.let { parameters.append("market", it.code) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Checks whether shows are saved in the current user's Your Library.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @return Wrapped Spotify API response. `data` contains per-item boolean flags from Spotify.
     */
    @Deprecated(
        "Spotify marks GET /v1/me/shows/contains as deprecated.",
    )
    suspend fun checkUsersSavedShows(ids: Ids): SpotifyApiResponse<List<Boolean>> {
        val endpoint = "https://api.spotify.com/v1/me/shows/contains"
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
}
