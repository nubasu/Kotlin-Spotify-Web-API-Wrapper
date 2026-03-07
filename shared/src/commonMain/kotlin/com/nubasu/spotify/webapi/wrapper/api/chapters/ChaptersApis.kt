package com.nubasu.spotify.webapi.wrapper.api.chapters

import com.nubasu.spotify.webapi.wrapper.api.BaseSpotifyApi
import com.nubasu.spotify.webapi.wrapper.api.SpotifyEndpoints
import com.nubasu.spotify.webapi.wrapper.api.SpotifyHttpClientFactory
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.chapters.Chapter
import com.nubasu.spotify.webapi.wrapper.response.chapters.Chapters
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.utils.CountryCode
import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder
import com.nubasu.spotify.webapi.wrapper.utils.TokenProvider
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom

/**
 * Chapter domain API for Spotify Web API.
 *
 * Covers chapter retrieval and chapter batches for audiobook and podcast content.
 */
class ChaptersApis(
    client: HttpClient = SpotifyHttpClientFactory.create(),
    tokenProvider: TokenProvider = TokenHolder,
) : BaseSpotifyApi(client, tokenProvider) {
    /**
     * Gets a Spotify chapter by chapter ID.
     *
     * @param id Spotify ID of the target resource for this endpoint.
     * @param market Market (country) code used to localize and filter content.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun getChapter(
        id: String,
        market: CountryCode? = null,
    ): SpotifyApiResponse<Chapter> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.CHAPTERS)
                    appendPathSegments(id)
                    market?.let { parameters.append("market", it.code) }
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets multiple Spotify chapters by their IDs.
     *
     * @param ids Spotify IDs of target resources (comma-separated at request time).
     * @param market Market (country) code used to localize and filter content.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify marks GET /v1/chapters as deprecated.",
    )
    suspend fun getSeveralChapters(
        ids: List<String>,
        market: CountryCode? = null,
    ): SpotifyApiResponse<Chapters> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.CHAPTERS)
                    parameters.append("ids", ids.joinToString(","))
                    market?.let { parameters.append("market", it.code) }
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }
}
