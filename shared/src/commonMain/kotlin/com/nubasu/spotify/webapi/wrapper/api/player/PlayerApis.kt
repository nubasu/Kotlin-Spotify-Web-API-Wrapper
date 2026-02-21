package com.nubasu.spotify.webapi.wrapper.api.player

import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.spotify.webapi.wrapper.request.common.Uris
import com.nubasu.spotify.webapi.wrapper.request.player.DeviceIds
import com.nubasu.spotify.webapi.wrapper.request.player.Offset
import com.nubasu.spotify.webapi.wrapper.request.player.StartResumePlaybackRequest
import com.nubasu.spotify.webapi.wrapper.request.player.State
import com.nubasu.spotify.webapi.wrapper.request.player.TransferPlaybackRequest
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.player.AvailableDevices
import com.nubasu.spotify.webapi.wrapper.response.player.CurrentlyPlayingTrack
import com.nubasu.spotify.webapi.wrapper.response.player.PlaybackState
import com.nubasu.spotify.webapi.wrapper.response.player.RecentlyPlayedTracks
import com.nubasu.spotify.webapi.wrapper.response.player.UsersQueue
import com.nubasu.spotify.webapi.wrapper.utils.CountryCode
import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

/**
 * Player domain API for Spotify Web API.
 *
 * Covers playback state, queue, device transfer, and playback control commands.
 */
class PlayerApis(
    private val client: HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        },
) {
    /**
     * Gets the current playback state for the current user.
     *
     * @param market Market (country) code used to localize and filter content.
     * @param additionalTypes Additional playable item types (for example `episode`).
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getPlaybackState(
        market: CountryCode? = null,
        additionalTypes: String? = null,
    ): SpotifyApiResponse<PlaybackState> {
        val endpoint = "https://api.spotify.com/v1/me/player"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    market?.let { parameters.append("market", it.code) }
                    additionalTypes?.let { parameters.append("additional_types", it) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Transfers playback to one of the user's Spotify Connect devices.
     *
     * @param deviceIds Spotify Connect device IDs that can receive transferred playback.
     * @param play True to start playback immediately after transfer.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    suspend fun transferPlayback(
        deviceIds: DeviceIds,
        play: Boolean = false,
    ): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/player"
        val response =
            client.put(endpoint) {
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                setBody(
                    TransferPlaybackRequest(
                        deviceIds = deviceIds.ids,
                        play = play,
                    ),
                )
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Gets available Spotify Connect devices for the current user.
     *
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getAvailableDevices(): SpotifyApiResponse<AvailableDevices> {
        val endpoint = "https://api.spotify.com/v1/me/player/devices"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets the currently playing item for the current user.
     *
     * @param market Market (country) code used to localize and filter content.
     * @param additionalTypes Additional playable item types (for example `episode`).
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getCurrentlyPlayingTrack(
        market: CountryCode? = null,
        additionalTypes: String? = null,
    ): SpotifyApiResponse<CurrentlyPlayingTrack> {
        val endpoint = "https://api.spotify.com/v1/me/player/currently-playing"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    market?.let { parameters.append("market", it.code) }
                    additionalTypes?.let { parameters.append("additional_types", it) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Starts or resumes playback on a Spotify Connect device.
     *
     * @param deviceId Spotify Connect device ID that should execute the playback action.
     * @param contextUri Context URI to play (album, artist, playlist, or show).
     * @param uris Spotify URIs of target resources.
     * @param offset Playback offset object selecting where playback starts in the context.
     * @param positionMs Playback position in milliseconds.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    suspend fun startResumePlayback(
        deviceId: String? = null,
        contextUri: String? = null,
        uris: Uris? = null,
        offset: Offset? = null,
        positionMs: Int? = null,
    ): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/player/play"
        val body =
            StartResumePlaybackRequest(
                contextUri = contextUri,
                uris = uris?.ids,
                offset = offset,
                positionMs = positionMs,
            )

        val response =
            client.put(endpoint) {
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                url {
                    deviceId?.let { parameters.append("device_id", it) }
                }
                setBody(body)
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Pauses playback on a Spotify Connect device.
     *
     * @param deviceId Spotify Connect device ID that should execute the playback action.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    suspend fun pausePlayback(deviceId: String? = null): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/player/pause"
        val response =
            client.put(endpoint) {
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                url {
                    deviceId?.let { parameters.append("device_id", it) }
                }
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Skips to the next item in the playback queue.
     *
     * @param deviceId Spotify Connect device ID that should execute the playback action.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    suspend fun skipToNext(deviceId: String? = null): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/player/next"
        val response =
            client.post(endpoint) {
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                url {
                    deviceId?.let { parameters.append("device_id", it) }
                }
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Skips to the previous item in the playback queue.
     *
     * @param deviceId Spotify Connect device ID that should execute the playback action.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    suspend fun skipToPrevious(deviceId: String? = null): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/player/previous"
        val response =
            client.post(endpoint) {
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                url {
                    deviceId?.let { parameters.append("device_id", it) }
                }
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Seeks playback to a position in milliseconds.
     *
     * @param positionMs Playback position in milliseconds.
     * @param deviceId Spotify Connect device ID that should execute the playback action.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    suspend fun seekToPosition(
        positionMs: Int,
        deviceId: String? = null,
    ): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/player/seek"
        val response =
            client.put(endpoint) {
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                url {
                    parameters.append("position_ms", positionMs.toString())
                    deviceId?.let { parameters.append("device_id", it) }
                }
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Sets playback repeat mode.
     *
     * @param state Repeat mode for playback (`track`, `context`, or `off`).
     * @param deviceId Spotify Connect device ID that should execute the playback action.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    suspend fun setRepeatMode(
        state: State,
        deviceId: String? = null,
    ): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/player/repeat"
        val response =
            client.put(endpoint) {
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                url {
                    parameters.append("state", state.repeatMode.value)
                    deviceId?.let { parameters.append("device_id", it) }
                }
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Sets playback volume percentage.
     *
     * @param volumePercent Target playback volume in percent (`0..100`).
     * @param deviceId Spotify Connect device ID that should execute the playback action.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    suspend fun setPlaybackVolume(
        volumePercent: Int,
        deviceId: String? = null,
    ): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/player/volume"
        val response =
            client.put(endpoint) {
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                url {
                    parameters.append("volume_percent", volumePercent.toString())
                    deviceId?.let { parameters.append("device_id", it) }
                }
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Enables or disables shuffle for playback.
     *
     * @param state True to enable shuffle, false to disable shuffle.
     * @param deviceId Spotify Connect device ID that should execute the playback action.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    suspend fun togglePlaybackShuffle(
        state: Boolean,
        deviceId: String? = null,
    ): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/player/shuffle"
        val response =
            client.put(endpoint) {
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                url {
                    parameters.append("state", state.toString())
                    deviceId?.let { parameters.append("device_id", it) }
                }
            }
        return response.toSpotifyBooleanApiResponse()
    }

    /**
     * Gets the current user's recently played tracks.
     *
     * @param limit Maximum number of items to return in one page.
     * @param after Cursor for fetching items played after a specific timestamp.
     * @param before Cursor for fetching items played before a specific timestamp.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getRecentlyPlayedTracks(
        limit: Int? = null,
        after: Long? = null,
        before: Long? = null,
    ): SpotifyApiResponse<RecentlyPlayedTracks> {
        val endpoint = "https://api.spotify.com/v1/me/player/recently-played"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    limit?.let { parameters.append("limit", limit.toString()) }
                    after?.let { parameters.append("after", after.toString()) }
                    before?.let { parameters.append("before", before.toString()) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets the current user's recently played tracks.
     *
     * @param limit Maximum number of items to return in one page.
     * @param after Cursor for fetching items played after a specific timestamp.
     * @param before Cursor for fetching items played before a specific timestamp.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Use Long for `after`/`before` unix timestamp in milliseconds.",
        ReplaceWith("getRecentlyPlayedTracks(limit = limit, after = after?.toLong(), before = before?.toLong())"),
    )
    suspend fun getRecentlyPlayedTracks(
        limit: Int?,
        after: Int?,
        before: Int?,
    ): SpotifyApiResponse<RecentlyPlayedTracks> =
        getRecentlyPlayedTracks(
            limit = limit,
            after = after?.toLong(),
            before = before?.toLong(),
        )

    /**
     * Gets the current user's playback queue.
     *
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getTheUsersQueue(): SpotifyApiResponse<UsersQueue> {
        val endpoint = "https://api.spotify.com/v1/me/player/queue"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Adds an item to the current user's playback queue.
     *
     * @param uri Spotify URI of the target resource.
     * @param deviceId Spotify Connect device ID that should execute the playback action.
     * @return Wrapped Spotify API response. `data` is `true` when Spotify accepted the operation.
     */
    suspend fun addItemToPlaybackQueue(
        uri: String,
        deviceId: String? = null,
    ): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/player/queue"
        val response =
            client.post(endpoint) {
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                url {
                    parameters.append("uri", uri)
                    deviceId?.let { parameters.append("device_id", it) }
                }
            }
        return response.toSpotifyBooleanApiResponse()
    }
}
