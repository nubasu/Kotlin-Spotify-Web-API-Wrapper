package com.nubasu.spotify.webapi.wrapper.api.artists

import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse

import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyBooleanApiResponse
import com.nubasu.spotify.webapi.wrapper.request.common.IncludeGroup
import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import com.nubasu.spotify.webapi.wrapper.response.artists.Artist
import com.nubasu.spotify.webapi.wrapper.response.artists.ArtistsAlbums
import com.nubasu.spotify.webapi.wrapper.response.artists.ArtistsTopTracks
import com.nubasu.spotify.webapi.wrapper.response.artists.Artists
import com.nubasu.spotify.webapi.wrapper.response.artists.ArtistsRelatedArtists
import com.nubasu.spotify.webapi.wrapper.utils.CountryCode
import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

class ArtistsApis(
    private val client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
) {

    suspend fun getArtist(
        id: String,
    ) : SpotifyApiResponse<Artist> {
        val ENDPOINT = "https://api.spotify.com/v1/artists/"
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                appendPathSegments(id)
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        return response.toSpotifyApiResponse()
    }

    @Deprecated(
        "Spotify marks GET /v1/artists as deprecated.",
    )
    suspend fun getSeveralArtists(
        ids: List<String>,
    ) : SpotifyApiResponse<Artists> {
        val ENDPOINT = "https://api.spotify.com/v1/artists"
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                parameters.append("ids", ids.joinToString(","))
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        return response.toSpotifyApiResponse()
    }

    suspend fun getArtistsAlbums(
        id: String,
        includeGroups: List<IncludeGroup> = emptyList(),
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ) : SpotifyApiResponse<ArtistsAlbums> {
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
        return response.toSpotifyApiResponse()
    }

    @Deprecated(
        "Spotify marks GET /v1/artists/{id}/top-tracks as deprecated.",
    )
    suspend fun getArtistsTopTracks(
        id: String,
        market: CountryCode? = null,
    ) : SpotifyApiResponse<ArtistsTopTracks> {
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
        return response.toSpotifyApiResponse()
    }

    @Deprecated(
        "Spotify marks GET /v1/artists/{id}/related-artists as deprecated.",
    )
    suspend fun getArtistsRelatedArtists(
        id: String,
    ) : SpotifyApiResponse<ArtistsRelatedArtists> {
        val ENDPOINT = "https://api.spotify.com/v1/artists"
        val response = client.get {
            url {
                takeFrom(ENDPOINT)
                appendPathSegments(id, "related-artists")
            }
            bearerAuth(TokenHolder.token)
            accept(ContentType.Application.Json)
        }
        return response.toSpotifyApiResponse()
    }
}
