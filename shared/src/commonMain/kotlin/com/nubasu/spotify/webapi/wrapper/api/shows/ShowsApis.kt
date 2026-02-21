package com.nubasu.spotify.webapi.wrapper.api.shows

import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.spotify.webapi.wrapper.request.common.Ids
import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.show.Show
import com.nubasu.spotify.webapi.wrapper.response.show.ShowEpisodes
import com.nubasu.spotify.webapi.wrapper.response.show.Shows
import com.nubasu.spotify.webapi.wrapper.response.show.UsersSavedShows
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

class ShowsApis(
    private val client: HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) { json() }
        },
) {
    suspend fun getShow(
        id: String,
        market: CountryCode? = null,
    ): SpotifyApiResponse<Show> {
        val endpoint = "https://api.spotify.com/v1/shows"
        val response =
            client.get {
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

    @Deprecated(
        "Spotify marks GET /v1/shows as deprecated.",
    )
    suspend fun getSeveralShows(
        ids: List<String>,
        market: CountryCode? = null,
    ): SpotifyApiResponse<Shows> {
        val endpoint = "https://api.spotify.com/v1/shows"
        val response =
            client.get {
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

    suspend fun getShowEpisodes(
        id: String,
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ): SpotifyApiResponse<ShowEpisodes> {
        val endpoint = "https://api.spotify.com/v1/shows"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    appendPathSegments(id, "episodes")
                    market?.let { parameters.append("market", it.code) }
                    pagingOptions.limit?.let { parameters.append("limit", it.toString()) }
                    pagingOptions.offset?.let { parameters.append("offset", it.toString()) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    suspend fun getUsersSavedShows(pagingOptions: PagingOptions = PagingOptions()): SpotifyApiResponse<UsersSavedShows> {
        val endpoint = "https://api.spotify.com/v1/me/shows"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    pagingOptions.limit?.let { parameters.append("limit", it.toString()) }
                    pagingOptions.offset?.let { parameters.append("offset", it.toString()) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    @Deprecated(
        "Spotify marks PUT /v1/me/shows as deprecated.",
    )
    suspend fun saveShowsForCurrentUser(ids: Ids): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/shows"
        val response =
            client.put {
                url {
                    takeFrom(endpoint)
                    parameters.append("ids", ids.ids.joinToString(","))
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyBooleanApiResponse()
    }

    @Deprecated(
        "Spotify marks DELETE /v1/me/shows as deprecated.",
    )
    suspend fun removeUsersSavedShows(
        ids: Ids,
        market: CountryCode? = null,
    ): SpotifyApiResponse<Boolean> {
        val endpoint = "https://api.spotify.com/v1/me/shows"
        val response =
            client.delete {
                url {
                    takeFrom(endpoint)
                    parameters.append("ids", ids.ids.joinToString(","))
                    market?.let { parameters.append("market", it.code) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyBooleanApiResponse()
    }

    @Deprecated(
        "Spotify marks GET /v1/me/shows/contains as deprecated.",
    )
    suspend fun checkUsersSavedShows(ids: Ids): SpotifyApiResponse<List<Boolean>> {
        val endpoint = "https://api.spotify.com/v1/me/shows/contains"
        val response =
            client.get {
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
