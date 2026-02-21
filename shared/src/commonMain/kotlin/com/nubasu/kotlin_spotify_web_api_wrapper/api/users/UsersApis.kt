package com.nubasu.kotlin_spotify_web_api_wrapper.api.users

import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.Ids
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
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

class UsersApis {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getCurrentUsersProfile(): User {
        val endpoint = "https://api.spotify.com/v1/me"
        val response = client.get(endpoint) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun getUsersTopItems(
        type: String,
        timeRange: String? = null,
        limit: Int? = null,
        offset: Int? = null,
    ): UsersTopItems {
        val endpoint = "https://api.spotify.com/v1/me/top"
        val response = client.get {
            url {
                takeFrom(endpoint)
                appendPathSegments(type)
                timeRange?.let { parameters.append("time_range", it) }
                limit?.let { parameters.append("limit", it.toString()) }
                offset?.let { parameters.append("offset", it.toString()) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun getUsersProfile(userId: String): UsersProfile {
        val endpoint = "https://api.spotify.com/v1/users"
        val response = client.get {
            url {
                takeFrom(endpoint)
                appendPathSegments(userId)
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun followPlaylist(playlistId: String, public: Boolean = true): Boolean {
        val endpoint = "https://api.spotify.com/v1/playlists"
        val response = client.put {
            url {
                takeFrom(endpoint)
                appendPathSegments(playlistId, "followers")
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            setBody(mapOf("public" to public))
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.status.isSuccess()
    }

    suspend fun unfollowPlaylist(playlistId: String): Boolean {
        val endpoint = "https://api.spotify.com/v1/playlists"
        val response = client.delete {
            url {
                takeFrom(endpoint)
                appendPathSegments(playlistId, "followers")
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.status.isSuccess()
    }

    suspend fun getFollowedArtists(limit: Int? = null, after: String? = null): FollowedArtists {
        val endpoint = "https://api.spotify.com/v1/me/following"
        val response = client.get {
            url {
                takeFrom(endpoint)
                parameters.append("type", "artist")
                limit?.let { parameters.append("limit", it.toString()) }
                after?.let { parameters.append("after", it) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun followArtistsOrUsers(type: String, ids: Ids): Boolean {
        val endpoint = "https://api.spotify.com/v1/me/following"
        val response = client.put {
            url {
                takeFrom(endpoint)
                parameters.append("type", type)
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            setBody(ids)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.status.isSuccess()
    }

    suspend fun unfollowArtistsOrUsers(type: String, ids: Ids): Boolean {
        val endpoint = "https://api.spotify.com/v1/me/following"
        val response = client.delete {
            url {
                takeFrom(endpoint)
                parameters.append("type", type)
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            setBody(ids)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.status.isSuccess()
    }

    suspend fun checkIfUserFollowsArtistsOrUsers(type: String, ids: Ids): List<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/following/contains"
        val response = client.get {
            url {
                takeFrom(endpoint)
                parameters.append("type", type)
                parameters.append("ids", ids.ids.joinToString(","))
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun checkIfCurrentUserFollowsPlaylist(playlistId: String, ids: Ids): List<Boolean> {
        val endpoint = "https://api.spotify.com/v1/playlists"
        val response = client.get {
            url {
                takeFrom(endpoint)
                appendPathSegments(playlistId, "followers", "contains")
                parameters.append("ids", ids.ids.joinToString(","))
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }
}
