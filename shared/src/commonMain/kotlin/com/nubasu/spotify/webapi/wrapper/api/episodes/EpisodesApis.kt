package com.nubasu.spotify.webapi.wrapper.api.episodes

import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.spotify.webapi.wrapper.request.common.Ids
import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.episodes.Episode
import com.nubasu.spotify.webapi.wrapper.response.episodes.Episodes
import com.nubasu.spotify.webapi.wrapper.response.episodes.UsersSavedEpisodes
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
 * Episode domain API for Spotify Web API.
 *
 * Covers episode metadata and the current user's saved podcast episodes.
 */
class EpisodesApis(
    private val client: HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        },
) {
    /**
     * Gets a Spotify episode by episode ID.
     *
     * @param id Spotify ID of the target resource for this endpoint.
     * @param market Market (country) code used to localize and filter content.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getEpisode(
        id: String,
        market: CountryCode? = null,
    ): SpotifyApiResponse<Episode> {
        val endpoint = "https://api.spotify.com/v1/episodes"
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
     * Gets multiple Spotify episodes by their IDs.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @param market Market (country) code used to localize and filter content.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify marks GET /v1/episodes as deprecated.",
    )
    suspend fun getSeveralEpisodes(
        ids: List<String>,
        market: CountryCode? = null,
    ): SpotifyApiResponse<Episodes> {
        val endpoint = "https://api.spotify.com/v1/episodes"
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
     * Gets the current user's saved episodes from Your Library.
     *
     * @param market Market (country) code used to localize and filter content.
     * @param pagingOptions Paging options (`limit`, `offset`) used for paged endpoints.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getUsersSavedEpisodes(
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ): SpotifyApiResponse<UsersSavedEpisodes> {
        val endpoint = "https://api.spotify.com/v1/me/episodes"
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
     * Saves episodes to the current user's Your Library.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    @Deprecated(
        "Spotify marks PUT /v1/me/episodes as deprecated.",
    )
    suspend fun saveEpisodesForCurrentUser(ids: Ids): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/episodes"
        val response =
            client.put(endpoint) {
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                url {
                    parameters.append("ids", ids.ids.joinToString(","))
                }
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Removes episodes from the current user's Your Library.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    @Deprecated(
        "Spotify marks DELETE /v1/me/episodes as deprecated.",
    )
    suspend fun removeUsersSavedEpisodes(ids: Ids): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/episodes"
        val response =
            client.delete(endpoint) {
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                url {
                    parameters.append("ids", ids.ids.joinToString(","))
                }
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Checks whether episodes are saved in the current user's Your Library.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @return Wrapped Spotify API response. `data` contains per-item boolean flags from Spotify.
     */
    @Deprecated(
        "Spotify marks GET /v1/me/episodes/contains as deprecated.",
    )
    suspend fun checkUsersSavedEpisodes(ids: Ids): SpotifyApiResponse<List<Boolean>> {
        val endpoint = "https://api.spotify.com/v1/me/episodes/contains"
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
