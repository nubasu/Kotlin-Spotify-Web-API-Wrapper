package com.nubasu.spotify.webapi.wrapper.api.playlists

import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import com.nubasu.spotify.webapi.wrapper.request.playlists.AddItemsToPlaylistRequest
import com.nubasu.spotify.webapi.wrapper.request.playlists.ChangePlaylistDetailsRequest
import com.nubasu.spotify.webapi.wrapper.request.playlists.CreatePlaylistRequest
import com.nubasu.spotify.webapi.wrapper.request.playlists.RemovePlaylistItemsRequest
import com.nubasu.spotify.webapi.wrapper.request.playlists.UpdatePlaylistItemsRequest
import com.nubasu.spotify.webapi.wrapper.response.common.ImageObject
import com.nubasu.spotify.webapi.wrapper.response.common.SnapshotIdResponse
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.playlists.CategorysPlaylists
import com.nubasu.spotify.webapi.wrapper.response.playlists.CurrentUsersPlaylists
import com.nubasu.spotify.webapi.wrapper.response.playlists.FeaturedPlaylists
import com.nubasu.spotify.webapi.wrapper.response.playlists.Playlist
import com.nubasu.spotify.webapi.wrapper.response.playlists.PlaylistItem
import com.nubasu.spotify.webapi.wrapper.response.playlists.SimplifiedPlaylistObject
import com.nubasu.spotify.webapi.wrapper.response.playlists.UsersPlaylist
import com.nubasu.spotify.webapi.wrapper.utils.CountryCode
import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

class PlaylistsApis(
    private val client: HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) { json() }
        },
) {
    suspend fun getPlaylist(
        playlistId: String,
        market: CountryCode? = null,
        fields: String? = null,
        additionalTypes: List<String> = listOf("track", "episode"),
    ): SpotifyApiResponse<Playlist> {
        val endpoint = "https://api.spotify.com/v1/playlists"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    appendPathSegments(playlistId)
                    market?.let { parameters.append("market", it.code) }
                    fields?.let { parameters.append("fields", it) }
                    parameters.append("additional_types", additionalTypes.joinToString(","))
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    suspend fun changePlaylistDetails(
        playlistId: String,
        body: ChangePlaylistDetailsRequest,
    ): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/playlists"
        val response =
            client.put {
                url {
                    takeFrom(endpoint)
                    appendPathSegments(playlistId)
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        return response.toSpotifyBooleanApiResponse()
    }

    suspend fun getPlaylistItems(
        playlistId: String,
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
        fields: String? = null,
        additionalTypes: List<String> = listOf("track", "episode"),
    ): SpotifyApiResponse<PlaylistItem> {
        val endpoint = "https://api.spotify.com/v1/playlists"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    appendPathSegments(playlistId, "items")
                    market?.let { parameters.append("market", it.code) }
                    pagingOptions.limit?.let { parameters.append("limit", it.toString()) }
                    pagingOptions.offset?.let { parameters.append("offset", it.toString()) }
                    fields?.let { parameters.append("fields", it) }
                    parameters.append("additional_types", additionalTypes.joinToString(","))
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    suspend fun updatePlaylistItems(
        playlistId: String,
        body: UpdatePlaylistItemsRequest? = null,
        uris: List<String>? = null,
    ): SpotifyApiResponse<SnapshotIdResponse> {
        val endpoint = "https://api.spotify.com/v1/playlists"
        val response =
            client.put {
                url {
                    takeFrom(endpoint)
                    appendPathSegments(playlistId, "items")
                    uris?.let { parameters.append("uris", it.joinToString(",")) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                body?.let { setBody(it) }
            }
        return response.toSpotifyApiResponse()
    }

    suspend fun addItemsToPlaylist(
        playlistId: String,
        body: AddItemsToPlaylistRequest? = null,
        uris: List<String>? = null,
        position: Int? = null,
    ): SpotifyApiResponse<SnapshotIdResponse> {
        val endpoint = "https://api.spotify.com/v1/playlists"
        val response =
            client.post {
                url {
                    takeFrom(endpoint)
                    appendPathSegments(playlistId, "items")
                    uris?.let { parameters.append("uris", it.joinToString(",")) }
                    position?.let { parameters.append("position", it.toString()) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                body?.let { setBody(it) }
            }
        return response.toSpotifyApiResponse()
    }

    suspend fun removePlaylistItems(
        playlistId: String,
        body: RemovePlaylistItemsRequest,
    ): SpotifyApiResponse<SnapshotIdResponse> {
        val endpoint = "https://api.spotify.com/v1/playlists"
        val response =
            client.delete {
                url {
                    takeFrom(endpoint)
                    appendPathSegments(playlistId, "items")
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        return response.toSpotifyApiResponse()
    }

    suspend fun getCurrentUsersPlaylists(pagingOptions: PagingOptions = PagingOptions()): SpotifyApiResponse<CurrentUsersPlaylists> {
        val endpoint = "https://api.spotify.com/v1/me/playlists"
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

    @Deprecated(
        "Spotify marks GET /v1/users/{user_id}/playlists as deprecated. Prefer getCurrentUsersPlaylists() when possible.",
        ReplaceWith("getCurrentUsersPlaylists(pagingOptions)"),
    )
    suspend fun getUsersPlaylists(
        userId: String,
        pagingOptions: PagingOptions = PagingOptions(),
    ): SpotifyApiResponse<UsersPlaylist> {
        val endpoint = "https://api.spotify.com/v1/users"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    appendPathSegments(userId, "playlists")
                    pagingOptions.limit?.let { parameters.append("limit", it.toString()) }
                    pagingOptions.offset?.let { parameters.append("offset", it.toString()) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    suspend fun createPlaylist(body: CreatePlaylistRequest): SpotifyApiResponse<SimplifiedPlaylistObject> {
        val endpoint = "https://api.spotify.com/v1/me/playlists"
        val response =
            client.post {
                url { takeFrom(endpoint) }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        return response.toSpotifyApiResponse()
    }

    @Deprecated(
        "Spotify recommends POST /v1/me/playlists. Use createPlaylist(body).",
        ReplaceWith("createPlaylist(body)"),
    )
    suspend fun createPlaylist(
        userId: String,
        body: CreatePlaylistRequest,
    ): SpotifyApiResponse<SimplifiedPlaylistObject> = createPlaylist(body)

    @Deprecated(
        "Spotify marks GET /v1/browse/featured-playlists as deprecated.",
    )
    suspend fun getFeaturedPlaylists(
        locale: String? = null,
        timestamp: String? = null,
        pagingOptions: PagingOptions = PagingOptions(),
        country: CountryCode? = null,
    ): SpotifyApiResponse<FeaturedPlaylists> {
        val endpoint = "https://api.spotify.com/v1/browse/featured-playlists"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    country?.let { parameters.append("country", it.code) }
                    locale?.let { parameters.append("locale", it) }
                    timestamp?.let { parameters.append("timestamp", it) }
                    pagingOptions.limit?.let { parameters.append("limit", it.toString()) }
                    pagingOptions.offset?.let { parameters.append("offset", it.toString()) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    @Deprecated(
        "Spotify marks GET /v1/browse/categories/{category_id}/playlists as deprecated.",
    )
    suspend fun getCategorysPlaylists(
        categoryId: String,
        pagingOptions: PagingOptions = PagingOptions(),
        country: CountryCode? = null,
    ): SpotifyApiResponse<CategorysPlaylists> {
        val endpoint = "https://api.spotify.com/v1/browse/categories"
        val response =
            client.get {
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
        return response.toSpotifyApiResponse()
    }

    suspend fun getPlaylistCoverImage(playlistId: String): SpotifyApiResponse<List<ImageObject>> {
        val endpoint = "https://api.spotify.com/v1/playlists"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    appendPathSegments(playlistId, "images")
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    suspend fun addCustomPlaylistCoverImage(
        playlistId: String,
        imageBase64Jpeg: String,
    ): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/playlists"
        val response =
            client.put {
                url {
                    takeFrom(endpoint)
                    appendPathSegments(playlistId, "images")
                }
                bearerAuth(TokenHolder.token)
                header(HttpHeaders.ContentType, "image/jpeg")
                setBody(imageBase64Jpeg)
            }
        return response.toSpotifyBooleanApiResponse()
    }
}
