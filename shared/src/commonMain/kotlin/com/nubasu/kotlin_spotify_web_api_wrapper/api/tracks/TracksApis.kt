package com.nubasu.kotlin_spotify_web_api_wrapper.api.tracks

import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.Ids
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.PagingOptions
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.OneTrackAudioFeatures
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.Recommendations
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.Track
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.Tracks
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.TracksAudioAnalysis
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.TracksAudioFeatures
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
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getTrack(
        id: String,
        market: CountryCode? = null,
    ): Track {
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
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun getSeveralTracks(
        ids: List<String>,
        market: CountryCode? = null,
    ): Tracks {
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
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun getUsersSavedTracks(
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ): UsersSavedTrack {
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
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun saveTracksForCurrentUser(body: Ids): Boolean {
        val endpoint = "https://api.spotify.com/v1/me/tracks"
        val response = client.put(endpoint) {
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

    suspend fun removeUsersSavedTracks(body: Ids): Boolean {
        val endpoint = "https://api.spotify.com/v1/me/tracks"
        val response = client.delete(endpoint) {
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
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    @Deprecated("Spotify marks this endpoint as deprecated")
    suspend fun getSeveralUsersAudioFeatures(ids: Ids): TracksAudioFeatures {
        val endpoint = "https://api.spotify.com/v1/audio-features"
        val response = client.get {
            url {
                takeFrom(endpoint)
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

    @Deprecated("Spotify marks this endpoint as deprecated")
    suspend fun getTracksAudioFeatures(id: String): OneTrackAudioFeatures {
        val endpoint = "https://api.spotify.com/v1/audio-features"
        val response = client.get {
            url {
                takeFrom(endpoint)
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

    @Deprecated("Spotify marks this endpoint as deprecated")
    suspend fun getTrackAudioAnalysis(id: String): TracksAudioAnalysis {
        val endpoint = "https://api.spotify.com/v1/audio-analysis"
        val response = client.get {
            url {
                takeFrom(endpoint)
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

    @Deprecated("Spotify marks this endpoint as deprecated")
    suspend fun getRecommendations(
        seedArtists: List<String>? = null,
        seedGenres: List<String>? = null,
        seedTracks: List<String>? = null,
        limit: Int? = null,
        market: CountryCode? = null,
    ): Recommendations {
        val endpoint = "https://api.spotify.com/v1/recommendations"
        val response = client.get {
            url {
                takeFrom(endpoint)
                seedArtists?.takeIf { it.isNotEmpty() }?.let { parameters.append("seed_artists", it.joinToString(",")) }
                seedGenres?.takeIf { it.isNotEmpty() }?.let { parameters.append("seed_genres", it.joinToString(",")) }
                seedTracks?.takeIf { it.isNotEmpty() }?.let { parameters.append("seed_tracks", it.joinToString(",")) }
                limit?.let { parameters.append("limit", it.toString()) }
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
}
