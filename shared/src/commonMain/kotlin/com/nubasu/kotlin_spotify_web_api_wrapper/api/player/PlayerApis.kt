package com.nubasu.kotlin_spotify_web_api_wrapper.api.player

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyApiResponse

import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.Uris
import com.nubasu.kotlin_spotify_web_api_wrapper.request.player.DeviceIds
import com.nubasu.kotlin_spotify_web_api_wrapper.request.player.Offset
import com.nubasu.kotlin_spotify_web_api_wrapper.request.player.StartResumePlaybackRequest
import com.nubasu.kotlin_spotify_web_api_wrapper.request.player.State
import com.nubasu.kotlin_spotify_web_api_wrapper.request.player.TransferPlaybackRequest
import com.nubasu.kotlin_spotify_web_api_wrapper.response.player.AvailableDevices
import com.nubasu.kotlin_spotify_web_api_wrapper.response.player.CurrentlyPlayingTrack
import com.nubasu.kotlin_spotify_web_api_wrapper.response.player.Device
import com.nubasu.kotlin_spotify_web_api_wrapper.response.player.PlaybackState
import com.nubasu.kotlin_spotify_web_api_wrapper.response.player.RecentlyPlayedTracks
import com.nubasu.kotlin_spotify_web_api_wrapper.response.player.UsersQueue
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.CountryCode
import com.nubasu.kotlin_spotify_web_api_wrapper.utils.TokenHolder
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

class PlayerApis(
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
) {

    suspend fun getPlaybackState(
        market: CountryCode? = null,
        additionalTypes: String? = null,
    ) : SpotifyApiResponse<PlaybackState> {
        val ENDPOINT = "https://api.spotify.com/v1/me/player"
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                market?.let { parameters.append("market", it.code) }
                additionalTypes?.let { parameters.append("additional_types", it) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return SpotifyApiResponse(response.status.value, response.body())
    }

    suspend fun transferPlayback(
        deviceIds: DeviceIds,
        play: Boolean = false,
    ) : SpotifyApiResponse<Boolean> {
        val ENDPOINT = "https://api.spotify.com/v1/me/player"
        val response = client.put(ENDPOINT) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            setBody(
                TransferPlaybackRequest(
                    deviceIds = deviceIds.ids,
                    play = play,
                )
            )
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return SpotifyApiResponse(response.status.value, response.status.isSuccess())
    }

    suspend fun getAvailableDevices() : SpotifyApiResponse<AvailableDevices> {
        val ENDPOINT = "https://api.spotify.com/v1/me/player/devices"
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return SpotifyApiResponse(response.status.value, response.body())
    }

    suspend fun getCurrentlyPlayingTrack(
        market: CountryCode? = null,
        additionalTypes: String? = null,
    ) : SpotifyApiResponse<CurrentlyPlayingTrack> {
        val ENDPOINT = "https://api.spotify.com/v1/me/player/currently-playing"
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                market?.let { parameters.append("market", it.code) }
                additionalTypes?.let { parameters.append("additional_types", it) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return SpotifyApiResponse(response.status.value, response.body())
    }

    suspend fun startResumePlayback(
        deviceId: String? = null,
        contextUri: String? = null,
        uris: Uris? = null,
        offset: Offset? = null,
        positionMs: Int? = null,
    ) : SpotifyApiResponse<Boolean> {
        val ENDPOINT = "https://api.spotify.com/v1/me/player/play"
        val body = StartResumePlaybackRequest(
            contextUri = contextUri,
            uris = uris?.ids,
            offset = offset,
            positionMs = positionMs,
        )

        val response = client.put(ENDPOINT) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            url {
                deviceId?.let { parameters.append("device_id", it) }
            }
            setBody(body)
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return SpotifyApiResponse(response.status.value, response.status.isSuccess())
    }

    suspend fun pausePlayback(
        deviceId: String? = null,
    ) : SpotifyApiResponse<Boolean> {
        val ENDPOINT = "https://api.spotify.com/v1/me/player/pause"
        val response = client.put(ENDPOINT) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            url {
                deviceId?.let { parameters.append("device_id", it) }
            }
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return SpotifyApiResponse(response.status.value, response.status.isSuccess())
    }

    suspend fun skipToNext(
        deviceId: String? = null,
    ) : SpotifyApiResponse<Boolean> {
        val ENDPOINT = "https://api.spotify.com/v1/me/player/next"
        val response = client.post(ENDPOINT) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            url {
                deviceId?.let { parameters.append("device_id", it) }
            }
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return SpotifyApiResponse(response.status.value, response.status.isSuccess())
    }

    suspend fun skipToPrevious(
        deviceId: String? = null,
    ) : SpotifyApiResponse<Boolean> {
        val ENDPOINT = "https://api.spotify.com/v1/me/player/previous"
        val response = client.post(ENDPOINT) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            url {
                deviceId?.let { parameters.append("device_id", it) }
            }
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return SpotifyApiResponse(response.status.value, response.status.isSuccess())
    }

    suspend fun seekToPosition(
        positionMs: Int,
        deviceId: String? = null,
    ) : SpotifyApiResponse<Boolean> {
        val ENDPOINT = "https://api.spotify.com/v1/me/player/seek"
        val response = client.put(ENDPOINT) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            url {
                parameters.append("position_ms", positionMs.toString())
                deviceId?.let { parameters.append("device_id", it) }
            }
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return SpotifyApiResponse(response.status.value, response.status.isSuccess())
    }

    suspend fun setRepeatMode(
        state: State,
        deviceId: String? = null,
    ) : SpotifyApiResponse<Boolean> {
        val ENDPOINT = "https://api.spotify.com/v1/me/player/repeat"
        val response = client.put(ENDPOINT) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            url {
                parameters.append("state", state.repeatMode.value)
                deviceId?.let { parameters.append("device_id", it) }
            }
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return SpotifyApiResponse(response.status.value, response.status.isSuccess())
    }

    suspend fun setPlaybackVolume(
        volumePercent: Int,
        deviceId: String? = null,
    ) : SpotifyApiResponse<Boolean> {
        val ENDPOINT = "https://api.spotify.com/v1/me/player/volume"
        val response = client.put(ENDPOINT) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            url {
                parameters.append("volume_percent", volumePercent.toString())
                deviceId?.let { parameters.append("device_id", it) }
            }
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return SpotifyApiResponse(response.status.value, response.status.isSuccess())
    }

    suspend fun togglePlaybackShuffle(
        state: Boolean,
        deviceId: String? = null,
    ) : SpotifyApiResponse<Boolean> {
        val ENDPOINT = "https://api.spotify.com/v1/me/player/shuffle"
        val response = client.put(ENDPOINT) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            url {
                parameters.append("state", state.toString())
                deviceId?.let { parameters.append("device_id", it) }
            }
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return SpotifyApiResponse(response.status.value, response.status.isSuccess())
    }

    suspend fun getRecentlyPlayedTracks(
        limit: Int? = null,
        after: Int? = null,
        before: Int? = null,
    ) : SpotifyApiResponse<RecentlyPlayedTracks> {
        val ENDPOINT = "https://api.spotify.com/v1/me/player/recently-played"
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                limit?.let { parameters.append("limit", limit.toString()) }
                after?.let { parameters.append("after", after.toString()) }
                before?.let { parameters.append("before", before.toString()) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return SpotifyApiResponse(response.status.value, response.body())
    }

    suspend fun getTheUsersQueue() : SpotifyApiResponse<UsersQueue> {
        val ENDPOINT = "https://api.spotify.com/v1/me/player/queue"
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return SpotifyApiResponse(response.status.value, response.body())
    }

    suspend fun addItemToPlaybackQueue(
        uri: String,
        deviceId: String? = null,
    ) : SpotifyApiResponse<Boolean> {
        val ENDPOINT = "https://api.spotify.com/v1/me/player/queue"
        val response = client.post(ENDPOINT) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            url {
                parameters.append("uri", uri)
                deviceId?.let { parameters.append("device_id", it) }
            }
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return SpotifyApiResponse(response.status.value, response.status.isSuccess())
    }
}
