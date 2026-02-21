package com.nubasu.spotify.webapi.wrapper.api.artists

import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.request.common.IncludeGroup
import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import com.nubasu.spotify.webapi.wrapper.response.artists.Artist
import com.nubasu.spotify.webapi.wrapper.response.artists.Artists
import com.nubasu.spotify.webapi.wrapper.response.artists.ArtistsAlbums
import com.nubasu.spotify.webapi.wrapper.response.artists.ArtistsRelatedArtists
import com.nubasu.spotify.webapi.wrapper.response.artists.ArtistsTopTracks
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
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

/**
 * Artist domain API for Spotify Web API.
 *
 * Covers artist profile retrieval, albums, top tracks, and related artists.
 */
class ArtistsApis(
    private val client: HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
        },
) {
    /**
     * Gets a Spotify artist by artist ID.
     *
     * @param id Spotify ID of the target resource for this endpoint.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getArtist(id: String): SpotifyApiResponse<Artist> {
        val endpoint = "https://api.spotify.com/v1/artists/"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    appendPathSegments(id)
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets multiple Spotify artists by their IDs.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify marks GET /v1/artists as deprecated.",
    )
    suspend fun getSeveralArtists(ids: List<String>): SpotifyApiResponse<Artists> {
        val endpoint = "https://api.spotify.com/v1/artists"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    parameters.append("ids", ids.joinToString(","))
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets albums released by a Spotify artist.
     *
     * @param id Spotify ID of the target resource for this endpoint.
     * @param includeGroups Album groups to include (`album`, `single`, `appears_on`, `compilation`).
     * @param market Market (country) code used to localize and filter content.
     * @param pagingOptions Paging options (`limit`, `offset`) used for paged endpoints.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getArtistsAlbums(
        id: String,
        includeGroups: List<IncludeGroup> = emptyList(),
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ): SpotifyApiResponse<ArtistsAlbums> {
        val endpoint = "https://api.spotify.com/v1/artists"
        val limit = pagingOptions.limit
        val offset = pagingOptions.offset
        val includeGroupsStr = includeGroups.map { it.value }
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    appendPathSegments(id, "albums")
                    if (includeGroups.isNotEmpty()) parameters.append("include_groups", includeGroupsStr.joinToString(","))
                    market?.let { parameters.append("market", it.code) }
                    limit?.let { parameters.append("limit", it.toString()) }
                    offset?.let { parameters.append("offset", it.toString()) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets top tracks for a Spotify artist.
     *
     * @param id Spotify ID of the target resource for this endpoint.
     * @param market Market (country) code used to localize and filter content.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify marks GET /v1/artists/{id}/top-tracks as deprecated.",
    )
    suspend fun getArtistsTopTracks(
        id: String,
        market: CountryCode? = null,
    ): SpotifyApiResponse<ArtistsTopTracks> {
        val endpoint = "https://api.spotify.com/v1/artists"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    appendPathSegments(id, "top-tracks")
                    market?.let { parameters.append("market", it.code) }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets artists related to a Spotify artist.
     *
     * @param id Spotify ID of the target resource for this endpoint.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify marks GET /v1/artists/{id}/related-artists as deprecated.",
    )
    suspend fun getArtistsRelatedArtists(id: String): SpotifyApiResponse<ArtistsRelatedArtists> {
        val endpoint = "https://api.spotify.com/v1/artists"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    appendPathSegments(id, "related-artists")
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }
}
