package com.nubasu.spotify.webapi.wrapper.api.users

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
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

class UsersApis(
    private val client: HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) { json() }
        },
) {
    suspend fun getCurrentUsersProfile(): SpotifyApiResponse<User> {
        val endpoint = "https://api.spotify.com/v1/me"
        val response =
            client.get(endpoint) {
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    @Deprecated(
        "Spotify marks GET /v1/me/top/{type} as deprecated.",
    )
    suspend fun getUsersTopItems(
        type: TopItemType,
        timeRange: TimeRange? = null,
        limit: Int? = null,
        offset: Int? = null,
    ): SpotifyApiResponse<UsersTopItems> {
        val endpoint = "https://api.spotify.com/v1/me/top/${type.value}"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    timeRange?.let { parameters.append("time_range", it.value) }
                    limit?.let { parameters.append("limit", it.toString()) }
                    offset?.let { parameters.append("offset", it.toString()) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    suspend fun getUsersProfile(userId: String): SpotifyApiResponse<UsersProfile> {
        val endpoint = "https://api.spotify.com/v1/users/$userId"
        val response =
            client.get(endpoint) {
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    @Deprecated(
        "Spotify marks PUT /v1/playlists/{playlist_id}/followers as deprecated.",
    )
    suspend fun followPlaylist(
        playlistId: String,
        public: Boolean = true,
    ): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/playlists/$playlistId/followers"
        val response =
            client.put(endpoint) {
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                setBody(FollowPlaylistRequest(public = public))
            }
        return response.toSpotifyBooleanApiResponse()
    }

    @Deprecated(
        "Spotify marks DELETE /v1/playlists/{playlist_id}/followers as deprecated.",
    )
    suspend fun unfollowPlaylist(playlistId: String): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/playlists/$playlistId/followers"
        val response =
            client.delete(endpoint) {
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyBooleanApiResponse()
    }

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
        val endpoint = "https://api.spotify.com/v1/me/following"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    parameters.append("type", type.value)
                    limit?.let { parameters.append("limit", it.toString()) }
                    after?.let { parameters.append("after", it) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    @Deprecated(
        "Spotify marks PUT /v1/me/following as deprecated.",
    )
    suspend fun followArtistsOrUsers(
        type: FollowType,
        ids: List<String>,
    ): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/following"
        val response =
            client.put {
                url {
                    takeFrom(endpoint)
                    parameters.append("type", type.value)
                    parameters.append("ids", ids.joinToString(","))
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyBooleanApiResponse()
    }

    @Deprecated(
        "Spotify marks DELETE /v1/me/following as deprecated.",
    )
    suspend fun unfollowArtistsOrUsers(
        type: FollowType,
        ids: List<String>,
    ): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/following"
        val response =
            client.delete {
                url {
                    takeFrom(endpoint)
                    parameters.append("type", type.value)
                    parameters.append("ids", ids.joinToString(","))
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyBooleanApiResponse()
    }

    @Deprecated(
        "Spotify marks GET /v1/me/following/contains as deprecated.",
    )
    suspend fun checkIfUserFollowsArtistsOrUsers(
        type: FollowType,
        ids: List<String>,
    ): SpotifyApiResponse<List<Boolean>> {
        val endpoint = "https://api.spotify.com/v1/me/following/contains"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    parameters.append("type", type.value)
                    parameters.append("ids", ids.joinToString(","))
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    @Deprecated(
        "Spotify marks GET /v1/playlists/{playlist_id}/followers/contains as deprecated.",
    )
    suspend fun checkIfCurrentUserFollowsPlaylist(
        playlistId: String,
        ids: List<String>,
    ): SpotifyApiResponse<List<Boolean>> {
        val endpoint = "https://api.spotify.com/v1/playlists/$playlistId/followers/contains"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    parameters.append("ids", ids.joinToString(","))
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }
}
