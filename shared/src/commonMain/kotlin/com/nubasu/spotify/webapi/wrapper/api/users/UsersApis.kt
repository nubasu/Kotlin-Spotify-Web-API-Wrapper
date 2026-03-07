package com.nubasu.spotify.webapi.wrapper.api.users

import com.nubasu.spotify.webapi.wrapper.api.BaseSpotifyApi
import com.nubasu.spotify.webapi.wrapper.api.SpotifyEndpoints
import com.nubasu.spotify.webapi.wrapper.api.SpotifyHttpClientFactory
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.spotify.webapi.wrapper.request.users.FollowPlaylistRequest
import com.nubasu.spotify.webapi.wrapper.request.users.FollowType
import com.nubasu.spotify.webapi.wrapper.request.users.TimeRange
import com.nubasu.spotify.webapi.wrapper.request.users.TopItemType
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.users.FollowedArtists
import com.nubasu.spotify.webapi.wrapper.response.users.User
import com.nubasu.spotify.webapi.wrapper.response.users.UsersProfile
import com.nubasu.spotify.webapi.wrapper.response.users.UsersTopItems
import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder
import com.nubasu.spotify.webapi.wrapper.utils.TokenProvider
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
 * User profile and follow domain API for Spotify Web API.
 *
 * Covers current user profile, top items, follows, and public user profiles.
 */
class UsersApis(
    client: HttpClient = SpotifyHttpClientFactory.create(),
    tokenProvider: TokenProvider = TokenHolder,
) : BaseSpotifyApi(client, tokenProvider) {
    /**
     * Gets the Spotify profile of the current user.
     *
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getCurrentUsersProfile(): SpotifyApiResponse<User> {
        val response =
            client.get(SpotifyEndpoints.ME) {
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets the current user's top artists or tracks.
     *
     * @param type Spotify API type parameter required by the endpoint.
     * @param timeRange Spotify ranking window (`short_term`, `medium_term`, `long_term`) for top items.
     * @param limit Maximum number of items to return in one page.
     * @param offset Index of the first item to return for paging.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify marks GET /v1/me/top/{type} as deprecated.",
    )
    suspend fun getUsersTopItems(
        type: TopItemType,
        timeRange: TimeRange? = null,
        limit: Int? = null,
        offset: Int? = null,
    ): SpotifyApiResponse<UsersTopItems> {
        val response =
            client.get {
                url {
                    takeFrom("${SpotifyEndpoints.ME_TOP}/${type.value}")
                    timeRange?.let { parameters.append("time_range", it.value) }
                    limit?.let { parameters.append("limit", it.toString()) }
                    offset?.let { parameters.append("offset", it.toString()) }
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets the public Spotify profile for a user.
     *
     * @param userId Spotify user ID.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getUsersProfile(userId: String): SpotifyApiResponse<UsersProfile> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.USERS)
                    appendPathSegments(userId)
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Follows a Spotify playlist for the current user.
     *
     * @param playlistId Spotify playlist ID.
     * @param public Whether the playlist follow should be publicly visible on the user profile.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    @Deprecated(
        "Spotify marks PUT /v1/playlists/{playlist_id}/followers as deprecated.",
    )
    suspend fun followPlaylist(
        playlistId: String,
        public: Boolean = true,
    ): SpotifyApiResponse<Boolean> {
        val response =
            client.put {
                url {
                    takeFrom(SpotifyEndpoints.PLAYLISTS)
                    appendPathSegments(playlistId, "followers")
                }
                spotifyAuth()
                contentType(ContentType.Application.Json)
                setBody(FollowPlaylistRequest(public = public))
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Unfollows a Spotify playlist for the current user.
     *
     * @param playlistId Spotify playlist ID.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    @Deprecated(
        "Spotify marks DELETE /v1/playlists/{playlist_id}/followers as deprecated.",
    )
    suspend fun unfollowPlaylist(playlistId: String): SpotifyApiResponse<Boolean> {
        val response =
            client.delete {
                url {
                    takeFrom(SpotifyEndpoints.PLAYLISTS)
                    appendPathSegments(playlistId, "followers")
                }
                spotifyAuth()
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Gets artists followed by the current user.
     *
     * @param type Spotify API type parameter required by the endpoint.
     * @param limit Maximum number of items to return in one page.
     * @param after Cursor ID of the last artist from the previous page (`after` parameter for follow pagination).
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify marks GET /v1/me/following as deprecated.",
    )
    suspend fun getFollowedArtists(
        type: FollowType = FollowType.ARTIST,
        limit: Int? = null,
        after: String? = null,
    ): SpotifyApiResponse<FollowedArtists> {
        require(type == FollowType.ARTIST) {
            "Spotify Get Followed Artists supports only type=artist."
        }
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ME_FOLLOWING)
                    parameters.append("type", type.value)
                    limit?.let { parameters.append("limit", it.toString()) }
                    after?.let { parameters.append("after", it) }
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Follows artists or users for the current user.
     *
     * @param type Spotify API type parameter required by the endpoint.
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    @Deprecated(
        "Spotify marks PUT /v1/me/following as deprecated.",
    )
    suspend fun followArtistsOrUsers(
        type: FollowType,
        ids: List<String>,
    ): SpotifyApiResponse<Boolean> {
        val response =
            client.put {
                url {
                    takeFrom(SpotifyEndpoints.ME_FOLLOWING)
                    parameters.append("type", type.value)
                    parameters.append("ids", ids.joinToString(","))
                }
                spotifyAuth()
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Unfollows artists or users for the current user.
     *
     * @param type Spotify API type parameter required by the endpoint.
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    @Deprecated(
        "Spotify marks DELETE /v1/me/following as deprecated.",
    )
    suspend fun unfollowArtistsOrUsers(
        type: FollowType,
        ids: List<String>,
    ): SpotifyApiResponse<Boolean> {
        val response =
            client.delete {
                url {
                    takeFrom(SpotifyEndpoints.ME_FOLLOWING)
                    parameters.append("type", type.value)
                    parameters.append("ids", ids.joinToString(","))
                }
                spotifyAuth()
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Checks whether the current user follows the specified artists or users.
     *
     * @param type Spotify API type parameter required by the endpoint.
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @return Wrapped Spotify API response. `data` contains per-item boolean flags from Spotify.
     */
    @Deprecated(
        "Spotify marks GET /v1/me/following/contains as deprecated.",
    )
    suspend fun checkIfUserFollowsArtistsOrUsers(
        type: FollowType,
        ids: List<String>,
    ): SpotifyApiResponse<List<Boolean>> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ME_FOLLOWING_CONTAINS)
                    parameters.append("type", type.value)
                    parameters.append("ids", ids.joinToString(","))
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Checks whether specific users follow a Spotify playlist.
     *
     * @param playlistId Spotify playlist ID.
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @return Wrapped Spotify API response. `data` contains per-item boolean flags from Spotify.
     */
    @Deprecated(
        "Spotify marks GET /v1/playlists/{playlist_id}/followers/contains as deprecated.",
    )
    suspend fun checkIfCurrentUserFollowsPlaylist(
        playlistId: String,
        ids: List<String>,
    ): SpotifyApiResponse<List<Boolean>> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.PLAYLISTS)
                    appendPathSegments(playlistId, "followers", "contains")
                    parameters.append("ids", ids.joinToString(","))
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }
}
