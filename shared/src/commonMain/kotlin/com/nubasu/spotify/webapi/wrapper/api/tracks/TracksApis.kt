package com.nubasu.spotify.webapi.wrapper.api.tracks

import com.nubasu.spotify.webapi.wrapper.api.BaseSpotifyApi
import com.nubasu.spotify.webapi.wrapper.api.SpotifyEndpoints
import com.nubasu.spotify.webapi.wrapper.api.SpotifyHttpClientFactory
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.spotify.webapi.wrapper.request.common.Ids
import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import com.nubasu.spotify.webapi.wrapper.request.tracks.RecommendationSeeds
import com.nubasu.spotify.webapi.wrapper.request.tracks.RecommendationTunableAttributes
import com.nubasu.spotify.webapi.wrapper.request.tracks.toQueryParams
import com.nubasu.spotify.webapi.wrapper.request.tracks.SaveTracksForCurrentUserRequest
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.tracks.OneTrackAudioFeatures
import com.nubasu.spotify.webapi.wrapper.response.tracks.Recommendations
import com.nubasu.spotify.webapi.wrapper.response.tracks.Track
import com.nubasu.spotify.webapi.wrapper.response.tracks.Tracks
import com.nubasu.spotify.webapi.wrapper.response.tracks.TracksAudioAnalysis
import com.nubasu.spotify.webapi.wrapper.response.tracks.TracksAudioFeatures
import com.nubasu.spotify.webapi.wrapper.response.tracks.UsersSavedTrack
import com.nubasu.spotify.webapi.wrapper.utils.CountryCode
import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder
import com.nubasu.spotify.webapi.wrapper.utils.TokenProvider
import com.nubasu.spotify.webapi.wrapper.utils.applyLimitOffsetPaging
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.takeFrom

/**
 * Track domain API for Spotify Web API.
 *
 * Covers track metadata, audio features/analysis, recommendations, and saved tracks.
 */
class TracksApis(
    client: HttpClient = SpotifyHttpClientFactory.create(),
    tokenProvider: TokenProvider = TokenHolder,
) : BaseSpotifyApi(client, tokenProvider) {
    /**
     * Gets a Spotify track by track ID.
     *
     * @param id Spotify ID of the target resource for this endpoint.
     * @param market Market (country) code used to localize and filter content.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getTrack(
        id: String,
        market: CountryCode? = null,
    ): SpotifyApiResponse<Track> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.TRACKS)
                    appendPathSegments(id)
                    market?.let { parameters.append("market", it.code) }
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets multiple Spotify tracks by their IDs.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @param market Market (country) code used to localize and filter content.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify marks GET /v1/tracks as deprecated.",
    )
    suspend fun getSeveralTracks(
        ids: List<String>,
        market: CountryCode? = null,
    ): SpotifyApiResponse<Tracks> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.TRACKS)
                    parameters.append("ids", ids.joinToString(","))
                    market?.let { parameters.append("market", it.code) }
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets the current user's saved tracks from Your Library.
     *
     * @param market Market (country) code used to localize and filter content.
     * @param pagingOptions Paging options (`limit`, `offset`) used for paged endpoints.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getUsersSavedTracks(
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ): SpotifyApiResponse<UsersSavedTrack> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ME_TRACKS)
                    market?.let { parameters.append("market", it.code) }
                    applyLimitOffsetPaging(pagingOptions)
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Saves tracks to the current user's Your Library.
     *
     * @param body Request payload object serialized for this endpoint.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    @Deprecated(
        "Spotify marks PUT /v1/me/tracks as deprecated.",
    )
    suspend fun saveTracksForCurrentUser(body: SaveTracksForCurrentUserRequest): SpotifyApiResponse<Boolean> {
        val response =
            client.put(SpotifyEndpoints.ME_TRACKS) {
                spotifyAuth()
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Removes tracks from the current user's Your Library.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    @Deprecated(
        "Spotify marks DELETE /v1/me/tracks as deprecated.",
    )
    suspend fun removeUsersSavedTracks(ids: Ids): SpotifyApiResponse<Boolean> {
        val response =
            client.delete {
                url {
                    takeFrom(SpotifyEndpoints.ME_TRACKS)
                    parameters.append("ids", ids.ids.joinToString(","))
                }
                spotifyAuth()
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Checks whether tracks are saved in the current user's Your Library.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @return Wrapped Spotify API response. `data` contains per-item boolean flags from Spotify.
     */
    suspend fun checkUsersSavedTracks(ids: Ids): SpotifyApiResponse<List<Boolean>> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ME_TRACKS_CONTAINS)
                    parameters.append("ids", ids.ids.joinToString(","))
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets audio features for a Spotify track.
     *
     * @param id Spotify ID of the target resource for this endpoint.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify marks GET /v1/audio-features/{id} as deprecated.",
    )
    suspend fun getTracksAudioFeatures(id: String): SpotifyApiResponse<OneTrackAudioFeatures> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.AUDIO_FEATURES)
                    appendPathSegments(id)
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets audio features for multiple Spotify tracks.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify marks GET /v1/audio-features as deprecated.",
    )
    suspend fun getSeveralTracksAudioFeatures(ids: List<String>): SpotifyApiResponse<TracksAudioFeatures> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.AUDIO_FEATURES)
                    parameters.append("ids", ids.joinToString(","))
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets full audio analysis for a Spotify track.
     *
     * @param id Spotify ID of the target resource for this endpoint.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify marks GET /v1/audio-analysis/{id} as deprecated.",
    )
    suspend fun getTracksAudioAnalysis(id: String): SpotifyApiResponse<TracksAudioAnalysis> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.AUDIO_ANALYSIS)
                    appendPathSegments(id)
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets track recommendations from Spotify based on seeds and tuning parameters.
     *
     * @param seeds Recommendation seed values (`seed_artists`, `seed_tracks`, `seed_genres`) used by Spotify.
     * @param market Market (country) code used to localize and filter content.
     * @param limit Maximum number of items to return in one page.
     * @param tunable Optional recommendation tuning attributes (`min_*`, `max_*`, `target_*` audio features).
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getRecommendations(
        seeds: RecommendationSeeds,
        market: CountryCode? = null,
        limit: Int? = null,
        tunable: RecommendationTunableAttributes = RecommendationTunableAttributes(),
    ): SpotifyApiResponse<Recommendations> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.RECOMMENDATIONS)
                    market?.let { parameters.append("market", it.code) }
                    limit?.let { parameters.append("limit", it.toString()) }
                    if (seeds.artists.isNotEmpty()) {
                        parameters.append("seed_artists", seeds.artists.joinToString(","))
                    }
                    if (seeds.genres.isNotEmpty()) {
                        parameters.append("seed_genres", seeds.genres.joinToString(","))
                    }
                    if (seeds.tracks.isNotEmpty()) {
                        parameters.append("seed_tracks", seeds.tracks.joinToString(","))
                    }
                    tunable.toQueryParams().forEach { (k, v) -> parameters.append(k, v) }
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }
}
