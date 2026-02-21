package com.nubasu.spotify.webapi.wrapper.api.albums

import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse

import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.spotify.webapi.wrapper.request.common.Ids
import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import com.nubasu.spotify.webapi.wrapper.response.albums.Album
import com.nubasu.spotify.webapi.wrapper.response.albums.AlbumTracks
import com.nubasu.spotify.webapi.wrapper.response.albums.Albums
import com.nubasu.spotify.webapi.wrapper.response.albums.NewRelease
import com.nubasu.spotify.webapi.wrapper.response.albums.UsersSavedAlbums
import com.nubasu.spotify.webapi.wrapper.utils.CountryCode
import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

class AlbumsApis(
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
) {

    suspend fun getAlbum(
        id: String,
        market: CountryCode? = null,
    ) : SpotifyApiResponse<Album> {
        val ENDPOINT = "https://api.spotify.com/v1/albums"
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                appendPathSegments(id)
                market?.let { parameters.append("market", market.code) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        return response.toSpotifyApiResponse()
    }

    @Deprecated(
        "Spotify marks GET /v1/albums as deprecated.",
    )
    suspend fun getSeveralAlbums(
        ids: List<String>,
        market: CountryCode? = null,
    ) : SpotifyApiResponse<Albums> {
        val ENDPOINT = "https://api.spotify.com/v1/albums"
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                parameters.append("ids", ids.joinToString(","))
                market?.let { parameters.append("market", market.code) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        return response.toSpotifyApiResponse()
    }

    suspend fun getAlbumTracks(
        id: String,
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ) : SpotifyApiResponse<AlbumTracks> {
        val ENDPOINT = "https://api.spotify.com/v1/albums"
        val limit = pagingOptions.limit
        val offset = pagingOptions.offset
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                appendPathSegments(id, "tracks")
                market?.let { parameters.append("market", it.code) }
                limit?.let { parameters.append("limit", it.toString()) }
                offset?.let { parameters.append("offset", it.toString()) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        return response.toSpotifyApiResponse()
    }

    suspend fun getUsersSavedAlbums(
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ) : SpotifyApiResponse<UsersSavedAlbums> {
        val ENDPOINT = "https://api.spotify.com/v1/me/albums"
        val limit = pagingOptions.limit
        val offset = pagingOptions.offset
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                market?.let { parameters.append("market", it.code) }
                limit?.let { parameters.append("limit", it.toString()) }
                offset?.let { parameters.append("offset", it.toString()) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        return response.toSpotifyApiResponse()
    }

    @Deprecated(
        "Spotify marks PUT /v1/me/albums as deprecated.",
    )
    suspend fun saveAlbumsForCurrentUser(
        body: Ids,
    ) : SpotifyApiResponse<Boolean> {
        val ENDPOINT = "https://api.spotify.com/v1/me/albums"
        val response = client.put(ENDPOINT) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            url {
                parameters.append("ids", body.ids.joinToString(","))
            }
        }
        return response.toSpotifyBooleanApiResponse()
    }

    @Deprecated(
        "Spotify marks DELETE /v1/me/albums as deprecated.",
    )
    suspend fun removeUsersSavedAlbums(
        body: Ids,
    ) : SpotifyApiResponse<Boolean> {
        val ENDPOINT = "https://api.spotify.com/v1/me/albums"
        val response = client.delete (ENDPOINT) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            url {
                parameters.append("ids", body.ids.joinToString(","))
            }
        }
        return response.toSpotifyBooleanApiResponse()
    }

    suspend fun checkUsersSavedAlbums(
        ids: Ids,
    ) : SpotifyApiResponse<List<Boolean>> {
        val ENDPOINT = "https://api.spotify.com/v1/me/albums/contains"
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                parameters.append("ids", ids.ids.joinToString(","))

            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        return response.toSpotifyApiResponse()
    }

    suspend fun getNewReleases(
        pagingOptions: PagingOptions = PagingOptions(),
        country: CountryCode? = null,
    ) : SpotifyApiResponse<NewRelease> {
        val ENDPOINT = "https://api.spotify.com/v1/browse/new-releases"
        val limit = pagingOptions.limit
        val offset = pagingOptions.offset
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                country?.let { parameters.append("country", it.code) }
                limit?.let { parameters.append("limit", it.toString()) }
                offset?.let { parameters.append("offset", it.toString()) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        return response.toSpotifyApiResponse()
    }
}
