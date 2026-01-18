package com.nubasu.kotlin_spotify_web_api_wrapper.api.artists

import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.IncludeGroup
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.PagingOptions
import com.nubasu.kotlin_spotify_web_api_wrapper.response.artists.Artist
import com.nubasu.kotlin_spotify_web_api_wrapper.response.artists.ArtistsAlbums
import com.nubasu.kotlin_spotify_web_api_wrapper.response.artists.ArtistsTopTracks
import com.nubasu.kotlin_spotify_web_api_wrapper.response.artists.Artists
import com.nubasu.kotlin_spotify_web_api_wrapper.response.artists.ArtistsRelatedArtists
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.CountryCode
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.TokenHolder
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

class ArtistsApis {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getArtist(
        id: String,
    ) : Artist {
        val ENDPOINT = "https://api.spotify.com/v1/artists/"
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                appendPathSegments(id)
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun getSeveralArtists(
        ids: List<String>,
    ) : Artists {
        val ENDPOINT = "https://api.spotify.com/v1/artists"
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                parameters.append("ids", ids.joinToString(","))
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun getArtistsAlbums(
        id: String,
        includeGroups: List<IncludeGroup> = emptyList(),
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ) : ArtistsAlbums {
        val ENDPOINT = "https://api.spotify.com/v1/artists"
        val limit = pagingOptions.limit
        val offset = pagingOptions.offset
        val includeGroupsStr = includeGroups.map { it.value }
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                appendPathSegments(id, "albums")
                if(includeGroups.isNotEmpty()) parameters.append("include_groups", includeGroupsStr.joinToString(","))
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

    suspend fun getArtistsTopTracks(
        id: String,
        market: CountryCode? = null,
    ) : ArtistsTopTracks {
        val ENDPOINT = "https://api.spotify.com/v1/artists"
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                appendPathSegments(id, "top-tracks")
                market?.let { parameters.append("market", it.code) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    @Deprecated("")
    suspend fun getArtistsRelatedArtists(
        id: String,
    ) : ArtistsRelatedArtists {
        val ENDPOINT = "https://api.spotify.com/v1/artists"
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                appendPathSegments(id, "related-artists")
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
