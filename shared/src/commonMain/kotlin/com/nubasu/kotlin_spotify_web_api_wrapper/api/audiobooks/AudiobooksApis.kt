package com.nubasu.kotlin_spotify_web_api_wrapper.api.audiobooks

import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.Ids
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.PagingOptions
import com.nubasu.kotlin_spotify_web_api_wrapper.response.audiobooks.Audiobook
import com.nubasu.kotlin_spotify_web_api_wrapper.response.audiobooks.AudiobookChapters
import com.nubasu.kotlin_spotify_web_api_wrapper.response.audiobooks.Audiobooks
import com.nubasu.kotlin_spotify_web_api_wrapper.response.audiobooks.UsersSavedAudiobooks
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
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

class AudiobooksApis {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getAnAudiobook(
        id: String,
        market: CountryCode? = null
    ) : Audiobook {
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
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun getSeveralAudiobooks(
        ids: List<String>,
        market: CountryCode? = null,
    ) : Audiobooks {
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
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun getAudiobookChapters(
        id: String,
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ) : AudiobookChapters {
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
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun getUsersSavedAudiobooks(
        pagingOptions: PagingOptions = PagingOptions(),
    ) : UsersSavedAudiobooks {
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
        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.body()
    }

    suspend fun saveAudiobooksForCurrentUser(
        ids: Ids,
    ) : Boolean {
        val ENDPOINT = "https://api.spotify.com/v1/me/audiobooks"
        val response = client.put(ENDPOINT) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            url {
                parameters.append("ids", ids.ids.joinToString(","))
            }
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.status.isSuccess()
    }

    suspend fun removeUsersSavedAudiobooks(
        ids: Ids,
    ) : Boolean {
        val ENDPOINT = "https://api.spotify.com/v1/me/audiobooks"
        val response = client.delete (ENDPOINT) {
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
            url {
                parameters.append("ids", ids.ids.joinToString(","))
            }
        }

        if (!response.status.isSuccess()) {
            throw RuntimeException("Spotify error ${response.status}: ${response.bodyAsText()}")
        }
        return response.status.isSuccess()
    }

    suspend fun checkUsersSavedAudiobooks(
        ids: Ids,
    ) : List<Boolean> {
        val ENDPOINT = "https://api.spotify.com/v1/me/audiobooks/contains"
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
}
