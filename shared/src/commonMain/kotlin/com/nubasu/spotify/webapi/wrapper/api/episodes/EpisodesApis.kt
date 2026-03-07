package com.nubasu.spotify.webapi.wrapper.api.episodes

import com.nubasu.spotify.webapi.wrapper.api.BaseSpotifyApi
import com.nubasu.spotify.webapi.wrapper.api.SpotifyEndpoints
import com.nubasu.spotify.webapi.wrapper.api.SpotifyHttpClientFactory
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
import com.nubasu.spotify.webapi.wrapper.utils.TokenProvider
import com.nubasu.spotify.webapi.wrapper.utils.applyLimitOffsetPaging
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom

/**
 * Episode domain API for Spotify Web API.
 *
 * Covers episode metadata and the current user's saved podcast episodes.
 */
class EpisodesApis(
    client: HttpClient = SpotifyHttpClientFactory.create(),
    tokenProvider: TokenProvider = TokenHolder,
) : BaseSpotifyApi(client, tokenProvider) {
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
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.EPISODES)
                    appendPathSegments(id)
                    market?.let { parameters.append("market", it.code) }
                }
                spotifyAuth()
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
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.EPISODES)
                    parameters.append("ids", ids.joinToString(","))
                    market?.let { parameters.append("market", it.code) }
                }
                spotifyAuth()
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
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ME_EPISODES)
                    market?.let { parameters.append("market", it.code) }
                    applyLimitOffsetPaging(pagingOptions)
                }
                spotifyAuth()
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
        val response =
            client.put(SpotifyEndpoints.ME_EPISODES) {
                spotifyAuth()
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
        val response =
            client.delete(SpotifyEndpoints.ME_EPISODES) {
                spotifyAuth()
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
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ME_EPISODES_CONTAINS)
                    parameters.append("ids", ids.ids.joinToString(","))
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }
}
