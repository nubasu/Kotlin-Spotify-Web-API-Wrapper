package com.nubasu.kotlin_spotify_web_api_wrapper.api.playlists

import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.PagingOptions
import com.nubasu.kotlin_spotify_web_api_wrapper.response.playlists.CategorysPlaylists
import com.nubasu.kotlin_spotify_web_api_wrapper.response.playlists.CurrentUsersPlaylists
import com.nubasu.kotlin_spotify_web_api_wrapper.response.playlists.FeaturedPlaylists
import com.nubasu.kotlin_spotify_web_api_wrapper.response.playlists.Playlist
import com.nubasu.kotlin_spotify_web_api_wrapper.response.playlists.PlaylistCoverImage
import com.nubasu.kotlin_spotify_web_api_wrapper.response.playlists.Tracks
import com.nubasu.kotlin_spotify_web_api_wrapper.response.playlists.UsersPlaylist
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.CountryCode
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.TokenHolder
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

class PlaylistsApis {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getPlaylist(
        playlistId: String,
        market: CountryCode? = null,
        fields: String? = null,
    ): Playlist {
        val endpoint = "https://api.spotify.com/v1/playlists"
        val response = client.get {
            url {
                takeFrom(endpoint)
                appendPathSegments(playlistId)
                market?.let { parameters.append("market", it.code) }
                fields?.let { parameters.append("fields", it) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun changePlaylistDetails(
        playlistId: String,
        name: String? = null,
        public: Boolean? = null,
        collaborative: Boolean? = null,
        description: String? = null,
    ): Boolean {
        val endpoint = "https://api.spotify.com/v1/playlists"
        val body = mutableMapOf<String, Any>()
        name?.let { body["name"] = it }
        public?.let { body["public"] = it }
        collaborative?.let { body["collaborative"] = it }
        description?.let { body["description"] = it }

        val response = client.put {
            url {
                takeFrom(endpoint)
                appendPathSegments(playlistId)
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            setBody(body)
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.status.isSuccess()
    }

    suspend fun getPlaylistItems(
        playlistId: String,
        market: CountryCode? = null,
        fields: String? = null,
        additionalTypes: String? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ): Tracks {
        val endpoint = "https://api.spotify.com/v1/playlists"
        val response = client.get {
            url {
                takeFrom(endpoint)
                appendPathSegments(playlistId, "tracks")
                market?.let { parameters.append("market", it.code) }
                fields?.let { parameters.append("fields", it) }
                additionalTypes?.let { parameters.append("additional_types", it) }
                pagingOptions.limit?.let { parameters.append("limit", it.toString()) }
                pagingOptions.offset?.let { parameters.append("offset", it.toString()) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun updatePlaylistItems(
        playlistId: String,
        uris: List<String>? = null,
        rangeStart: Int? = null,
        insertBefore: Int? = null,
        rangeLength: Int? = null,
        snapshotId: String? = null,
    ): Boolean {
        val endpoint = "https://api.spotify.com/v1/playlists"
        val body = mutableMapOf<String, Any>()
        uris?.let { body["uris"] = it }
        rangeStart?.let { body["range_start"] = it }
        insertBefore?.let { body["insert_before"] = it }
        rangeLength?.let { body["range_length"] = it }
        snapshotId?.let { body["snapshot_id"] = it }

        val response = client.put {
            url {
                takeFrom(endpoint)
                appendPathSegments(playlistId, "tracks")
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            setBody(body)
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.status.isSuccess()
    }

    suspend fun addItemsToPlaylist(
        playlistId: String,
        uris: List<String>,
        position: Int? = null,
    ): Boolean {
        val endpoint = "https://api.spotify.com/v1/playlists"
        val response = client.post {
            url {
                takeFrom(endpoint)
                appendPathSegments(playlistId, "tracks")
                position?.let { parameters.append("position", it.toString()) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            setBody(mapOf("uris" to uris))
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.status.isSuccess()
    }

    suspend fun removePlaylistItems(
        playlistId: String,
        uris: List<String>,
        snapshotId: String? = null,
    ): Boolean {
        val endpoint = "https://api.spotify.com/v1/playlists"
        val tracks = uris.map { mapOf("uri" to it) }
        val body = mutableMapOf<String, Any>("tracks" to tracks)
        snapshotId?.let { body["snapshot_id"] = it }

        val response = client.delete {
            url {
                takeFrom(endpoint)
                appendPathSegments(playlistId, "tracks")
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            setBody(body)
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.status.isSuccess()
    }

    suspend fun getCurrentUsersPlaylists(pagingOptions: PagingOptions = PagingOptions()): CurrentUsersPlaylists {
        val endpoint = "https://api.spotify.com/v1/me/playlists"
        val response = client.get {
            url {
                takeFrom(endpoint)
                pagingOptions.limit?.let { parameters.append("limit", it.toString()) }
                pagingOptions.offset?.let { parameters.append("offset", it.toString()) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun getUsersPlaylists(
        userId: String,
        pagingOptions: PagingOptions = PagingOptions(),
    ): UsersPlaylist {
        val endpoint = "https://api.spotify.com/v1/users"
        val response = client.get {
            url {
                takeFrom(endpoint)
                appendPathSegments(userId, "playlists")
                pagingOptions.limit?.let { parameters.append("limit", it.toString()) }
                pagingOptions.offset?.let { parameters.append("offset", it.toString()) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun createPlaylist(
        userId: String,
        name: String,
        public: Boolean = true,
        collaborative: Boolean = false,
        description: String? = null,
    ): Playlist {
        val endpoint = "https://api.spotify.com/v1/users"
        val body = mutableMapOf<String, Any>(
            "name" to name,
            "public" to public,
            "collaborative" to collaborative,
        )
        description?.let { body["description"] = it }

        val response = client.post {
            url {
                takeFrom(endpoint)
                appendPathSegments(userId, "playlists")
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            setBody(body)
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    @Deprecated("Spotify marks this endpoint as deprecated")
    suspend fun getFeaturedPlaylists(
        locale: String? = null,
        country: CountryCode? = null,
        timestamp: String? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ): FeaturedPlaylists {
        val endpoint = "https://api.spotify.com/v1/browse/featured-playlists"
        val response = client.get {
            url {
                takeFrom(endpoint)
                locale?.let { parameters.append("locale", it) }
                country?.let { parameters.append("country", it.code) }
                timestamp?.let { parameters.append("timestamp", it) }
                pagingOptions.limit?.let { parameters.append("limit", it.toString()) }
                pagingOptions.offset?.let { parameters.append("offset", it.toString()) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    @Deprecated("Spotify marks this endpoint as deprecated")
    suspend fun getCategorysPlaylists(
        categoryId: String,
        country: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ): CategorysPlaylists {
        val endpoint = "https://api.spotify.com/v1/browse/categories"
        val response = client.get {
            url {
                takeFrom(endpoint)
                appendPathSegments(categoryId, "playlists")
                country?.let { parameters.append("country", it.code) }
                pagingOptions.limit?.let { parameters.append("limit", it.toString()) }
                pagingOptions.offset?.let { parameters.append("offset", it.toString()) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun getPlaylistCoverImage(playlistId: String): List<PlaylistCoverImage> {
        val endpoint = "https://api.spotify.com/v1/playlists"
        val response = client.get {
            url {
                takeFrom(endpoint)
                appendPathSegments(playlistId, "images")
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun addCustomPlaylistCoverImage(
        playlistId: String,
        base64JpegImage: String,
    ): Boolean {
        val endpoint = "https://api.spotify.com/v1/playlists"
        val response = client.put {
            url {
                takeFrom(endpoint)
                appendPathSegments(playlistId, "images")
            }
            bearerAuth(TokenHolder.token)
            contentType(ContentType.Image.JPEG)
            setBody(base64JpegImage)
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.status.isSuccess()
    }
}
