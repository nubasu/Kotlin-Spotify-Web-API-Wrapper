package com.nubasu.kotlin_spotify_web_api_wrapper.api.albums

import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.Ids
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.PagingOptions
import com.nubasu.kotlin_spotify_web_api_wrapper.response.albums.Album
import com.nubasu.kotlin_spotify_web_api_wrapper.response.albums.AlbumTracks
import com.nubasu.kotlin_spotify_web_api_wrapper.response.albums.Albums
import com.nubasu.kotlin_spotify_web_api_wrapper.response.albums.NewRelease
import com.nubasu.kotlin_spotify_web_api_wrapper.response.albums.UsersSavedAlbums
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
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

class AlbumsApis {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getAlbum(
        id: String,
        market: CountryCode? = null,
    ): Album {
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
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun getSeveralAlbums(
        ids: List<String>,
        market: CountryCode? = null,
    ) : Albums {
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
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun getAlbumTracks(
        id: String,
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ) : AlbumTracks {
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
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun getUsersSavedAlbums(
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ) : UsersSavedAlbums {
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
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun saveAlbumsForCurrentUser(
        body: Ids,
    ) : Boolean {
        val ENDPOINT = "https://api.spotify.com/v1/me/albums"
        val response = client.put(ENDPOINT) {
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

    suspend fun removeUsersSavedAlbums(
        body: Ids,
    ) : Boolean {
        val ENDPOINT = "https://api.spotify.com/v1/me/albums"
        val response = client.delete (ENDPOINT) {
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

    suspend fun checkUsersSavedAlbums(
        ids: Ids,
    ) : List<Boolean> {
        val ENDPOINT = "https://api.spotify.com/v1/me/albums/contains"
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
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

    suspend fun getNewReleases(
        pagingOptions: PagingOptions = PagingOptions(),
    ) : NewRelease {
        val ENDPOINT = "https://api.spotify.com/v1/browse/new-releases"
        val limit = pagingOptions.limit
        val offset = pagingOptions.offset
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
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
}
