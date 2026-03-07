package com.nubasu.spotify.webapi.wrapper.api.artists

import com.nubasu.spotify.webapi.wrapper.api.BaseSpotifyApi
import com.nubasu.spotify.webapi.wrapper.api.SpotifyEndpoints
import com.nubasu.spotify.webapi.wrapper.api.SpotifyHttpClientFactory
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
import com.nubasu.spotify.webapi.wrapper.utils.TokenProvider
import com.nubasu.spotify.webapi.wrapper.utils.applyLimitOffsetPaging
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom

/**
 * Artist domain API for Spotify Web API.
 *
 * Covers artist profile retrieval, albums, top tracks, and related artists.
 */
class ArtistsApis(
    client: HttpClient = SpotifyHttpClientFactory.create(),
    tokenProvider: TokenProvider = TokenHolder,
) : BaseSpotifyApi(client, tokenProvider) {
    /**
     * Gets a Spotify artist by artist ID.
     *
     * @param id Spotify ID of the target resource for this endpoint.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getArtist(id: String): SpotifyApiResponse<Artist> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ARTISTS)
                    appendPathSegments(id)
                }
                spotifyAuth()
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
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ARTISTS)
                    parameters.append("ids", ids.joinToString(","))
                }
                spotifyAuth()
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
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ARTISTS)
                    appendPathSegments(id, "albums")
                    if (includeGroups.isNotEmpty()) {
                        parameters.append("include_groups", includeGroups.joinToString(",") { it.value })
                    }
                    market?.let { parameters.append("market", it.code) }
                    applyLimitOffsetPaging(pagingOptions)
                }
                spotifyAuth()
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
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ARTISTS)
                    appendPathSegments(id, "top-tracks")
                    market?.let { parameters.append("market", it.code) }
                }
                spotifyAuth()
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
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.ARTISTS)
                    appendPathSegments(id, "related-artists")
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }
}
