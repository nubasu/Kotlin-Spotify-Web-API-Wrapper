package com.nubasu.kotlin_spotify_web_api_wrapper.api.tracks

import com.nubasu.kotlin_spotify_web_api_wrapper.api.toSpotifyApiResponse

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyApiResponse
import com.nubasu.kotlin_spotify_web_api_wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.Ids
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.PagingOptions
import com.nubasu.kotlin_spotify_web_api_wrapper.request.tracks.SaveTracksForCurrentUserRequest
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.Track
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.Tracks
import com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.UsersSavedTrack
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.CountryCode
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.TokenHolder
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

class TracksApis(
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) { json() }
    }
) {

    suspend fun getTrack(id: String, market: CountryCode? = null) : SpotifyApiResponse<Track> {
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
return response.toSpotifyApiResponse()
    }

    suspend fun getSeveralTracks(ids: List<String>, market: CountryCode? = null) : SpotifyApiResponse<Tracks> {
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
return response.toSpotifyApiResponse()
    }

    suspend fun getUsersSavedTracks(market: CountryCode? = null, pagingOptions: PagingOptions = PagingOptions()) : SpotifyApiResponse<UsersSavedTrack> {
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
return response.toSpotifyApiResponse()
    }

    suspend fun saveTracksForCurrentUser(body: SaveTracksForCurrentUserRequest) : SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/tracks"
        val response = client.put(endpoint) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            setBody(body)
        }
return response.toSpotifyBooleanApiResponse()
    }

    suspend fun removeUsersSavedTracks(ids: Ids) : SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/tracks"
        val response = client.delete {
            url {
                takeFrom(endpoint)
                parameters.append("ids", ids.ids.joinToString(","))
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
return response.toSpotifyBooleanApiResponse()
    }

    suspend fun checkUsersSavedTracks(ids: Ids) : SpotifyApiResponse<List<Boolean>> {
        val endpoint = "https://api.spotify.com/v1/me/tracks/contains"
        val response = client.get {
            url {
                takeFrom(endpoint)
                parameters.append("ids", ids.ids.joinToString(","))
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
return response.toSpotifyApiResponse()
    }
}
