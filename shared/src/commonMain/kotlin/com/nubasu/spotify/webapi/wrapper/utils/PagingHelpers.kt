package com.nubasu.spotify.webapi.wrapper.utils

import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyResponseData
import io.ktor.http.Url

object PagingHelpers {
    fun nextPagingOptions(nextUrl: String?): PagingOptions? {
        val raw = nextUrl?.trim()?.takeIf { it.isNotEmpty() } ?: return null
        val normalized = if (raw.startsWith("?")) "https://dummy.local$raw" else raw
        val parsed = runCatching { Url(normalized) }.getOrNull() ?: return null
        val limit = parsed.parameters["limit"]?.toIntOrNull()
        val offset = parsed.parameters["offset"]?.toIntOrNull()
        if (limit == null && offset == null) return null
        return PagingOptions(limit = limit, offset = offset)
    }

    suspend fun <TPage, TItem> collectAllItems(
        firstPageResponse: SpotifyApiResponse<TPage>,
        nextUrlSelector: (TPage) -> String?,
        itemsSelector: (TPage) -> List<TItem>,
        fetchNextPage: suspend (PagingOptions) -> SpotifyApiResponse<TPage>,
        maxPages: Int = 1000,
    ): SpotifyApiResponse<List<TItem>> {
        require(maxPages >= 1) { "maxPages must be >= 1" }
        return when (val firstData = firstPageResponse.data) {
            is SpotifyResponseData.Error -> SpotifyApiResponse(
                statusCode = firstPageResponse.statusCode,
                data = firstData,
                headers = firstPageResponse.headers,
            )
            is SpotifyResponseData.Success -> {
                val collected = mutableListOf<TItem>()
                var currentData = firstData.value
                var pagesVisited = 1
                while (true) {
                    collected += itemsSelector(currentData)
                    if (pagesVisited >= maxPages) {
                        break
                    }
                    val next = nextPagingOptions(nextUrlSelector(currentData)) ?: break
                    val nextResponse = fetchNextPage(next)
                    when (val nextData = nextResponse.data) {
                        is SpotifyResponseData.Error -> {
                            return SpotifyApiResponse(
                                statusCode = nextResponse.statusCode,
                                data = nextData,
                                headers = nextResponse.headers,
                            )
                        }
                        is SpotifyResponseData.Success -> {
                            currentData = nextData.value
                            pagesVisited += 1
                        }
                    }
                }
                SpotifyApiResponse(
                    statusCode = firstPageResponse.statusCode,
                    data = SpotifyResponseData.Success(collected),
                    headers = firstPageResponse.headers,
                )
            }
        }
    }

    suspend fun <TPage, TItem> collectAllItems(
        initialPagingOptions: PagingOptions = PagingOptions(),
        fetchFirstPage: suspend (PagingOptions) -> SpotifyApiResponse<TPage>,
        nextUrlSelector: (TPage) -> String?,
        itemsSelector: (TPage) -> List<TItem>,
        fetchNextPage: suspend (PagingOptions) -> SpotifyApiResponse<TPage>,
        maxPages: Int = 1000,
    ): SpotifyApiResponse<List<TItem>> {
        val first = fetchFirstPage(initialPagingOptions)
        return collectAllItems(
            firstPageResponse = first,
            nextUrlSelector = nextUrlSelector,
            itemsSelector = itemsSelector,
            fetchNextPage = fetchNextPage,
            maxPages = maxPages,
        )
    }
}
