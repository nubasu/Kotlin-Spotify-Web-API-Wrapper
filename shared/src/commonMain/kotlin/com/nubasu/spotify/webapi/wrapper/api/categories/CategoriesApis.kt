package com.nubasu.spotify.webapi.wrapper.api.categories

import com.nubasu.spotify.webapi.wrapper.api.BaseSpotifyApi
import com.nubasu.spotify.webapi.wrapper.api.SpotifyEndpoints
import com.nubasu.spotify.webapi.wrapper.api.SpotifyHttpClientFactory
import com.nubasu.spotify.webapi.wrapper.api.toSpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import com.nubasu.spotify.webapi.wrapper.response.categories.BrowseCategories
import com.nubasu.spotify.webapi.wrapper.response.categories.BrowseCategory
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder
import com.nubasu.spotify.webapi.wrapper.utils.TokenProvider
import com.nubasu.spotify.webapi.wrapper.utils.applyLimitOffsetPaging
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom

/**
 * Browse category domain API for Spotify Web API.
 *
 * Covers category listing and individual category metadata from the Browse endpoints.
 */
class CategoriesApis(
    client: HttpClient = SpotifyHttpClientFactory.create(),
    tokenProvider: TokenProvider = TokenHolder,
) : BaseSpotifyApi(client, tokenProvider) {
    /**
     * Gets Spotify browse categories.
     *
     * @param locale Locale used for localized strings (for example `en_US`).
     * @param pagingOptions Paging options (`limit`, `offset`) used for paged endpoints.
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify marks GET /v1/browse/categories as deprecated.",
    )
    suspend fun getSeveralBrowseCategories(
        locale: String? = null,
        pagingOptions: PagingOptions = PagingOptions(),
    ): SpotifyApiResponse<BrowseCategories> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.CATEGORIES)
                    locale?.let { parameters.append("locale", it) }
                    applyLimitOffsetPaging(pagingOptions)
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }

    /**
     * Gets a Spotify browse category by category ID.
     *
     * @param categoryId Spotify category ID from the Browse API (for example `party` or `workout`).
     * @param locale Locale used for localized strings (for example `en_US`).
     * @return Wrapped Spotify API response with status code and parsed Spotify payload.
     */
    @Deprecated(
        "Spotify marks GET /v1/browse/categories/{category_id} as deprecated.",
    )
    suspend fun getSingleBrowseCategory(
        categoryId: String,
        locale: String? = null,
    ): SpotifyApiResponse<BrowseCategory> {
        val response =
            client.get {
                url {
                    takeFrom(SpotifyEndpoints.CATEGORIES)
                    appendPathSegments(categoryId)
                    locale?.let { parameters.append("locale", it) }
                }
                spotifyAuth()
            }
        return response.toSpotifyApiResponse()
    }
}
