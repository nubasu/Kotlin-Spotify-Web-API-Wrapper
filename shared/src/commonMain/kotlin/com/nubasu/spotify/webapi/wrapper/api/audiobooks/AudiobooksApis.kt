package com.nubasu.spotify.webapi.wrapper.api.audiobooks

import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse

import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.spotify.webapi.wrapper.request.common.Ids
import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import com.nubasu.spotify.webapi.wrapper.response.audiobooks.Audiobook
import com.nubasu.spotify.webapi.wrapper.response.audiobooks.AudiobookChapters
import com.nubasu.spotify.webapi.wrapper.response.audiobooks.Audiobooks
import com.nubasu.spotify.webapi.wrapper.response.audiobooks.UsersSavedAudiobooks
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

class AudiobooksApis(
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
) {

    suspend fun getAnAudiobook(
        id: String,
        market: CountryCode? = null
    ) : SpotifyApiResponse<Audiobook> {
        val ENDPOINT = "https://api.spotify.com/v1/audiobooks"
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
        "Spotify marks GET /v1/audiobooks as deprecated.",
    )
    suspend fun getSeveralAudiobooks(
        ids: List<String>,
        market: CountryCode? = null,
    ) : SpotifyApiResponse<Audiobooks> {
        val ENDPOINT = "https://api.spotify.com/v1/audiobooks"
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

    suspend fun getAudiobookChapters(
        id: String,
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ) : SpotifyApiResponse<AudiobookChapters> {
        val ENDPOINT = "https://api.spotify.com/v1/audiobooks"
        val limit = pagingOptions.limit
        val offset = pagingOptions.offset
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                appendPathSegments(id, "chapters")
                market?.let { parameters.append("market", it.code) }
                limit?.let { parameters.append("limit", it.toString()) }
                offset?.let { parameters.append("offset", it.toString()) }
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        return response.toSpotifyApiResponse()
    }

    suspend fun getUsersSavedAudiobooks(
        pagingOptions: PagingOptions = PagingOptions(),
    ) : SpotifyApiResponse<UsersSavedAudiobooks> {
        val ENDPOINT = "https://api.spotify.com/v1/me/audiobooks"
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
        return response.toSpotifyApiResponse()
    }

    @Deprecated(
        "Spotify marks PUT /v1/me/audiobooks as deprecated.",
    )
    suspend fun saveAudiobooksForCurrentUser(
        ids: Ids,
    ) : SpotifyApiResponse<Boolean> {
        val ENDPOINT = "https://api.spotify.com/v1/me/audiobooks"
        val response = client.put(ENDPOINT) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            url {
                parameters.append("ids", ids.ids.joinToString(","))
            }
        }
        return response.toSpotifyBooleanApiResponse()
    }

    @Deprecated(
        "Spotify marks DELETE /v1/me/audiobooks as deprecated.",
    )
    suspend fun removeUsersSavedAudiobooks(
        ids: Ids,
    ) : SpotifyApiResponse<Boolean> {
        val ENDPOINT = "https://api.spotify.com/v1/me/audiobooks"
        val response = client.delete (ENDPOINT) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            url {
                parameters.append("ids", ids.ids.joinToString(","))
            }
        }
        return response.toSpotifyBooleanApiResponse()
    }

    @Deprecated(
        "Spotify marks GET /v1/me/audiobooks/contains as deprecated.",
    )
    suspend fun checkUsersSavedAudiobooks(
        ids: Ids,
    ) : SpotifyApiResponse<List<Boolean>> {
        val ENDPOINT = "https://api.spotify.com/v1/me/audiobooks/contains"
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
}
