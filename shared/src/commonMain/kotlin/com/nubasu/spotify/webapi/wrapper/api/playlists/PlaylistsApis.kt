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

/**
 * Playlist domain API for Spotify Web API.
 *
 * Covers playlist metadata, items, creation, cover images, and browse playlist endpoints.
 */
class PlaylistsApis(
    private val client: HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) { json() }
        },
) {
    /**
     * Gets a Spotify playlist by playlist ID.
     *
     * @param playlistId Spotify playlist ID.
     * @param market Market (country) code used to localize and filter content.
     * @param fields Spotify `fields` filter expression to limit nested properties in the playlist response.
     * @param additionalTypes Additional playable item types (for example `episode`).
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
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

    /**
     * Changes metadata for a Spotify playlist.
     *
     * @param playlistId Spotify playlist ID.
     * @param body Request payload object serialized for this endpoint.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
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

    /**
     * Gets items in a Spotify playlist.
     *
     * @param playlistId Spotify playlist ID.
     * @param market Market (country) code used to localize and filter content.
     * @param pagingOptions Paging options (`limit`, `offset`) used for paged endpoints.
     * @param fields Spotify `fields` filter expression to limit nested properties in each playlist item.
     * @param additionalTypes Additional playable item types (for example `episode`).
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
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

    /**
     * Reorders or replaces items in a Spotify playlist.
     *
     * @param playlistId Spotify playlist ID.
     * @param body Request payload object serialized for this endpoint.
     * @param uris Spotify URIs of target resources.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
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

    /**
     * Adds items to a Spotify playlist.
     *
     * @param playlistId Spotify playlist ID.
     * @param body Request payload object serialized for this endpoint.
     * @param uris Spotify URIs of target resources.
     * @param position Zero-based insertion index in the target playlist.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
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

    /**
     * Removes items from a Spotify playlist.
     *
     * @param playlistId Spotify playlist ID.
     * @param body Request payload object serialized for this endpoint.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
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

    /**
     * Gets playlists owned or followed by the current user.
     *
     * @param pagingOptions Paging options (`limit`, `offset`) used for paged endpoints.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
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

    /**
     * Gets public playlists for a Spotify user.
     *
     * @param userId Spotify user ID.
     * @param pagingOptions Paging options (`limit`, `offset`) used for paged endpoints.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
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

    /**
     * Creates a Spotify playlist for the current user.
     *
     * @param body Request payload object serialized for this endpoint.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
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

    /**
     * Creates a Spotify playlist for the current user.
     *
     * @param userId Spotify user ID.
     * @param body Request payload object serialized for this endpoint.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify recommends POST /v1/me/playlists. Use createPlaylist(body).",
        ReplaceWith("createPlaylist(body)"),
    )
    suspend fun createPlaylist(
        userId: String,
        body: CreatePlaylistRequest,
    ): SpotifyApiResponse<SimplifiedPlaylistObject> = createPlaylist(body)

    /**
     * Gets Spotify featured playlists.
     *
     * @param locale Locale used for localized strings (for example `en_US`).
     * @param timestamp Optional date-time used by Spotify to select the featured message set (ISO 8601, UTC offset supported).
     * @param pagingOptions Paging options (`limit`, `offset`) used for paged endpoints.
     * @param country Country code used for market-specific filtering.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
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

    /**
     * Gets playlists for a Spotify browse category.
     *
     * @param categoryId Spotify category ID from the Browse API.
     * @param pagingOptions Paging options (`limit`, `offset`) used for paged endpoints.
     * @param country Country code used for market-specific filtering.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
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

    /**
     * Gets custom cover images for a Spotify playlist.
     *
     * @param playlistId Spotify playlist ID.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
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

    /**
     * Uploads a custom cover image for a Spotify playlist.
     *
     * @param playlistId Spotify playlist ID.
     * @param imageBase64Jpeg Base64-encoded JPEG image data for the playlist cover upload.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
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
