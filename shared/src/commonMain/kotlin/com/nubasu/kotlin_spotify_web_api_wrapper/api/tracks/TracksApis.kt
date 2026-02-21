package com.nubasu.kotlin_spotify_web_api_wrapper.api.tracks

import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.Ids
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.PagingOptions
import com.nubasu.kotlin_spotify_web_api_wrapper.request.tracks.SaveTracksForCurrentUserRequest
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.Track
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.Tracks
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.UsersSavedTrack
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

class TracksApis {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) { json() }
    }

    suspend fun getTrack(id: String, market: CountryCode? = null): Track {
        val endpoint = "https://api.spotify.com/v1/tracks"
        val response = client.get {
            url {
                takeFrom(endpoint)
                appendPathSegments(id)
                market?.let { parameters.append("market", it.code) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        return response.body()
    }

    suspend fun getSeveralTracks(ids: List<String>, market: CountryCode? = null): Tracks {
        val endpoint = "https://api.spotify.com/v1/tracks"
        val response = client.get {
            url {
                takeFrom(endpoint)
                parameters.append("ids", ids.joinToString(","))
                market?.let { parameters.append("market", it.code) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        return response.body()
    }

    suspend fun getUsersSavedTracks(market: CountryCode? = null, pagingOptions: PagingOptions = PagingOptions()): UsersSavedTrack {
        val endpoint = "https://api.spotify.com/v1/me/tracks"
        val response = client.get {
            url {
                takeFrom(endpoint)
                market?.let { parameters.append("market", it.code) }
                pagingOptions.limit?.let { parameters.append("limit", it.toString()) }
                pagingOptions.offset?.let { parameters.append("offset", it.toString()) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        return response.body()
    }

    suspend fun saveTracksForCurrentUser(body: SaveTracksForCurrentUserRequest): Boolean {
        val endpoint = "https://api.spotify.com/v1/me/tracks"
        val response = client.put(endpoint) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        if (!response.status.isSuccess()) throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        return response.status.isSuccess()
    }

    suspend fun removeUsersSavedTracks(ids: Ids): Boolean {
        val endpoint = "https://api.spotify.com/v1/me/tracks"
        val response = client.delete {
            url {
                takeFrom(endpoint)
                parameters.append("ids", ids.ids.joinToString(","))
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        return response.status.isSuccess()
    }

    suspend fun checkUsersSavedTracks(ids: Ids): List<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/tracks/contains"
        val response = client.get {
            url {
                takeFrom(endpoint)
                parameters.append("ids", ids.ids.joinToString(","))
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        return response.body()
    }
}
