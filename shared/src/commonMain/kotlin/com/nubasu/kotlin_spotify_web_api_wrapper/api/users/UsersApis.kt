package com.nubasu.kotlin_spotify_web_api_wrapper.api.users

import com.nubasu.kotlin_spotify_web_api_wrapper.request.users.FollowPlaylistRequest
import com.nubasu.kotlin_spotify_web_api_wrapper.request.users.FollowType
import com.nubasu.kotlin_spotify_web_api_wrapper.request.users.TimeRange
import com.nubasu.kotlin_spotify_web_api_wrapper.request.users.TopItemType
import com.nubasu.kotlin_spotify_web_api_wrapper.response.users.FollowedArtists
import com.nubasu.kotlin_spotify_web_api_wrapper.response.users.User
import com.nubasu.kotlin_spotify_web_api_wrapper.response.users.UsersProfile
import com.nubasu.kotlin_spotify_web_api_wrapper.response.users.UsersTopItems
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.TokenHolder
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

class UsersApis {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) { json() }
    }

    suspend fun getCurrentUsersProfile(): User {
        val endpoint = "https://api.spotify.com/v1/me"
        val response = client.get(endpoint) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        return response.body()
    }

    suspend fun getUsersTopItems(
        type: TopItemType,
        timeRange: TimeRange? = null,
        limit: Int? = null,
        offset: Int? = null,
    ): UsersTopItems {
        val endpoint = "https://api.spotify.com/v1/me/top/${type.value}"
        val response = client.get {
            url {
                takeFrom(endpoint)
                timeRange?.let { parameters.append("time_range", it.value) }
                limit?.let { parameters.append("limit", it.toString()) }
                offset?.let { parameters.append("offset", it.toString()) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        return response.body()
    }

    suspend fun getUsersProfile(userId: String): UsersProfile {
        val endpoint = "https://api.spotify.com/v1/users/$userId"
        val response = client.get(endpoint) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        return response.body()
    }

    suspend fun followPlaylist(playlistId: String, public: Boolean = true): Boolean {
        val endpoint = "https://api.spotify.com/v1/playlists/$playlistId/followers"
        val response = client.put(endpoint) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            setBody(FollowPlaylistRequest(public = public))
        }
        if (!response.status.isSuccess()) throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        return true
    }

    suspend fun unfollowPlaylist(playlistId: String): Boolean {
        val endpoint = "https://api.spotify.com/v1/playlists/$playlistId/followers"
        val response = client.delete(endpoint) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        return true
    }

    suspend fun getFollowedArtists(type: FollowType = FollowType.ARTIST, limit: Int? = null, after: String? = null): FollowedArtists {
        require(type == FollowType.ARTIST) {
            "Spotify Get Followed Artists supports only type=artist."
        }
        val endpoint = "https://api.spotify.com/v1/me/following"
        val response = client.get {
            url {
                takeFrom(endpoint)
                parameters.append("type", type.value)
                limit?.let { parameters.append("limit", it.toString()) }
                after?.let { parameters.append("after", it) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        return response.body()
    }

    suspend fun followArtistsOrUsers(type: FollowType, ids: List<String>): Boolean {
        val endpoint = "https://api.spotify.com/v1/me/following"
        val response = client.put {
            url {
                takeFrom(endpoint)
                parameters.append("type", type.value)
                parameters.append("ids", ids.joinToString(","))
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        return true
    }

    suspend fun unfollowArtistsOrUsers(type: FollowType, ids: List<String>): Boolean {
        val endpoint = "https://api.spotify.com/v1/me/following"
        val response = client.delete {
            url {
                takeFrom(endpoint)
                parameters.append("type", type.value)
                parameters.append("ids", ids.joinToString(","))
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        return true
    }

    suspend fun checkIfUserFollowsArtistsOrUsers(type: FollowType, ids: List<String>): List<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/following/contains"
        val response = client.get {
            url {
                takeFrom(endpoint)
                parameters.append("type", type.value)
                parameters.append("ids", ids.joinToString(","))
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        return response.body()
    }

    suspend fun checkIfCurrentUserFollowsPlaylist(playlistId: String, ids: List<String>): List<Boolean> {
        val endpoint = "https://api.spotify.com/v1/playlists/$playlistId/followers/contains"
        val response = client.get {
            url {
                takeFrom(endpoint)
                parameters.append("ids", ids.joinToString(","))
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        return response.body()
    }
}
