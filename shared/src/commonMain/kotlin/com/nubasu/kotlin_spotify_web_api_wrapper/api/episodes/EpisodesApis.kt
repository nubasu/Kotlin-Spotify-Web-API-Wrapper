package com.nubasu.kotlin_spotify_web_api_wrapper.api.episodes

import com.nubasu.kotlin_spotify_web_api_wrapper.api.toSpotifyApiResponse

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyApiResponse
import com.nubasu.kotlin_spotify_web_api_wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.Ids
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.PagingOptions
import com.nubasu.kotlin_spotify_web_api_wrapper.response.episodes.Episode
import com.nubasu.kotlin_spotify_web_api_wrapper.response.episodes.Episodes
import com.nubasu.kotlin_spotify_web_api_wrapper.response.episodes.UsersSavedEpisodes
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
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

class EpisodesApis(
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
) {

    suspend fun getEpisode(
        id: String,
        market: CountryCode? = null,
    ) : SpotifyApiResponse<Episode> {
        val ENDPOINT = "https://api.spotify.com/v1/episodes"
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
        "Spotify marks GET /v1/episodes as deprecated.",
    )
    suspend fun getSeveralEpisodes(
        ids: List<String>,
        market: CountryCode? = null,
    ) : SpotifyApiResponse<Episodes> {
        val ENDPOINT = "https://api.spotify.com/v1/episodes"
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

    suspend fun getUsersSavedEpisodes(
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ) : SpotifyApiResponse<UsersSavedEpisodes> {
        val ENDPOINT = "https://api.spotify.com/v1/me/episodes"
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
        "Spotify marks PUT /v1/me/episodes as deprecated.",
    )
    suspend fun saveEpisodesForCurrentUser(
        ids: Ids,
    ) : SpotifyApiResponse<Boolean> {
        val ENDPOINT = "https://api.spotify.com/v1/me/episodes"
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
        "Spotify marks DELETE /v1/me/episodes as deprecated.",
    )
    suspend fun removeUsersSavedEpisodes(
        ids: Ids,
    ) : SpotifyApiResponse<Boolean> {
        val ENDPOINT = "https://api.spotify.com/v1/me/episodes"
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
        "Spotify marks GET /v1/me/episodes/contains as deprecated.",
    )
    suspend fun checkUsersSavedEpisodes(
        ids: Ids,
    ) : SpotifyApiResponse<List<Boolean>> {
        val ENDPOINT = "https://api.spotify.com/v1/me/episodes/contains"
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
