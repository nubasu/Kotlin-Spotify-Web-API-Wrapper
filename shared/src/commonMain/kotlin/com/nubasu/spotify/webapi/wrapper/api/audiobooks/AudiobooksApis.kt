package com.nubasu.spotify.webapi.wrapper.api.audiobooks

import com.nubasu.spotify.webapi.wrapper.api.BaseSpotifyApi
import com.nubasu.spotify.webapi.wrapper.api.SpotifyEndpoints
import com.nubasu.spotify.webapi.wrapper.api.SpotifyHttpClientFactory
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.spotify.webapi.wrapper.request.common.Ids
import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import com.nubasu.spotify.webapi.wrapper.response.audiobooks.Audiobook
import com.nubasu.spotify.webapi.wrapper.response.audiobooks.AudiobookChapters
import com.nubasu.spotify.webapi.wrapper.response.audiobooks.Audiobooks
import com.nubasu.spotify.webapi.wrapper.response.audiobooks.UsersSavedAudiobooks
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
 * Audiobook domain API for Spotify Web API.
 *
 * Covers audiobook metadata, chapters, and the user's saved audiobooks in Your Library.
 */
class AudiobooksApis(
    client: HttpClient = SpotifyHttpClientFactory.create(),
    tokenProvider: TokenProvider = TokenHolder,
) : BaseSpotifyApi(client, tokenProvider) {
    /**
     * Gets a Spotify audiobook by audiobook ID.
     *
     * @param id Spotify ID of the target resource for this endpoint.
     * @param market Market (country) code used to localize and filter content.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getAudiobook(
        id: String,
        market: CountryCode? = null,
    ): SpotifyApiResponse<Audiobook> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.AUDIOBOOKS)
                    appendPathSegments(id)
                    market?.let { parameters.append("market", it.code) }
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets multiple Spotify audiobooks by their IDs.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @param market Market (country) code used to localize and filter content.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify marks GET /v1/audiobooks as deprecated.",
    )
    suspend fun getSeveralAudiobooks(
        ids: List<String>,
        market: CountryCode? = null,
    ): SpotifyApiResponse<Audiobooks> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.AUDIOBOOKS)
                    parameters.append("ids", ids.joinToString(","))
                    market?.let { parameters.append("market", it.code) }
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets chapters for a Spotify audiobook.
     *
     * @param id Spotify ID of the target resource for this endpoint.
     * @param market Market (country) code used to localize and filter content.
     * @param pagingOptions Paging options (`limit`, `offset`) used for paged endpoints.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getAudiobookChapters(
        id: String,
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ): SpotifyApiResponse<AudiobookChapters> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.AUDIOBOOKS)
                    appendPathSegments(id, "chapters")
                    market?.let { parameters.append("market", it.code) }
                    applyLimitOffsetPaging(pagingOptions)
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets the current user's saved audiobooks from Your Library.
     *
     * @param pagingOptions Paging options (`limit`, `offset`) used for paged endpoints.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getUsersSavedAudiobooks(pagingOptions: PagingOptions = PagingOptions()): SpotifyApiResponse<UsersSavedAudiobooks> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ME_AUDIOBOOKS)
                    applyLimitOffsetPaging(pagingOptions)
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Saves audiobooks to the current user's Your Library.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    @Deprecated(
        "Spotify marks PUT /v1/me/audiobooks as deprecated.",
    )
    suspend fun saveAudiobooksForCurrentUser(ids: Ids): SpotifyApiResponse<Boolean> {
        val response =
            client.put(SpotifyEndpoints.ME_AUDIOBOOKS) {
                spotifyAuth()
                url {
                    parameters.append("ids", ids.ids.joinToString(","))
                }
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Removes audiobooks from the current user's Your Library.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    @Deprecated(
        "Spotify marks DELETE /v1/me/audiobooks as deprecated.",
    )
    suspend fun removeUsersSavedAudiobooks(ids: Ids): SpotifyApiResponse<Boolean> {
        val response =
            client.delete(SpotifyEndpoints.ME_AUDIOBOOKS) {
                spotifyAuth()
                url {
                    parameters.append("ids", ids.ids.joinToString(","))
                }
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Checks whether audiobooks are saved in the current user's Your Library.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @return Wrapped Spotify API response. `data` contains per-item boolean flags from Spotify.
     */
    @Deprecated(
        "Spotify marks GET /v1/me/audiobooks/contains as deprecated.",
    )
    suspend fun checkUsersSavedAudiobooks(ids: Ids): SpotifyApiResponse<List<Boolean>> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ME_AUDIOBOOKS_CONTAINS)
                    parameters.append("ids", ids.ids.joinToString(","))
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }
}
