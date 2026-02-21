package com.nubasu.spotify.webapi.wrapper.api.search

import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import com.nubasu.spotify.webapi.wrapper.request.search.SearchType
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.search.SearchResponse
import com.nubasu.spotify.webapi.wrapper.utils.CountryCode
import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json

/**
 * Search domain API for Spotify Web API.
 *
 * Provides catalog search across albums, artists, tracks, playlists, and other resource types.
 */
class SearchApis(
    private val client: HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) { json() }
        },
) {
    /**
     * Searches the Spotify catalog for albums, artists, tracks, playlists, and other item types.
     *
     * @param q Search query text sent to Spotify catalog search.
     * @param types Resource types to include in the Spotify search (`album`, `artist`, `track`, `playlist`, etc.).
     * @param market Market (country) code used to localize and filter content.
     * @param pagingOptions Paging options (`limit`, `offset`) used for paged endpoints.
     * @param includeExternalAudio Whether to include externally hosted audio in search results (`include_external=audio`).
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    suspend fun searchForItem(
        q: String,
        types: Set<SearchType>,
        market: CountryCode? = null,
        pagingOptions: PagingOptions = PagingOptions(),
        includeExternalAudio: Boolean = false,
    ): SpotifyApiResponse<SearchResponse> {
        require(types.isNotEmpty()) { "types must not be empty" }
        val endpoint = "https://api.spotify.com/v1/search"
        val response =
            client.get {
                url {
                    takeFrom(endpoint)
                    parameters.append("q", q)
                    parameters.append("type", types.joinToString(",") { it.value })
                    market?.let { parameters.append("market", it.code) }
                    pagingOptions.limit?.let { parameters.append("limit", it.toString()) }
                    pagingOptions.offset?.let { parameters.append("offset", it.toString()) }
                    if (includeExternalAudio) {
                        parameters.append("include_external", "audio")
                    }
                }
                bearerAuth(TokenHolder.token)
                accept(ContentType.Application.Json)
            }
        return response.toSpotifyApiResponse()
    }
}
