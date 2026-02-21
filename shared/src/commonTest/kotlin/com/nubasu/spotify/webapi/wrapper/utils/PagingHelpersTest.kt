package com.nubasu.spotify.webapi.wrapper.utils

import com.nubasu.spotify.webapi.wrapper.request.common.PagingOptions
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyApiResponse
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyError
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyErrorResponse
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyResponseData
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

private data class TestPage(
    val items: List<Int>,
    val next: String?,
)

class PagingHelpersTest {
    @Test
    fun nextPagingOptions_parsesFromAbsoluteUrl() {
        val options = PagingHelpers.nextPagingOptions("https://api.spotify.com/v1/me/tracks?limit=20&offset=40")
        assertNotNull(options)
        assertEquals(20, options.limit)
        assertEquals(40, options.offset)
    }

    @Test
    fun nextPagingOptions_parsesFromQueryOnly() {
        val options = PagingHelpers.nextPagingOptions("?offset=10&limit=5")
        assertNotNull(options)
        assertEquals(5, options.limit)
        assertEquals(10, options.offset)
    }

    @Test
    fun nextPagingOptions_returnsNull_forInvalidInput() {
        assertNull(PagingHelpers.nextPagingOptions(null))
        assertNull(PagingHelpers.nextPagingOptions("https://example.com/no-query"))
        assertNull(PagingHelpers.nextPagingOptions(":::bad-url:::"))
    }

    @Test
    fun collectAllItems_returnsFirstErrorWithoutFetchingNext() =
        runTest {
            val first: SpotifyApiResponse<TestPage> =
                SpotifyApiResponse(
                    statusCode = 429,
                    data =
                        SpotifyResponseData.Error(
                            SpotifyErrorResponse(
                                SpotifyError(429, "too many requests"),
                            ),
                        ),
                )
            var called = false
            val response =
                PagingHelpers.collectAllItems<TestPage, Int>(
                    firstPageResponse = first,
                    nextUrlSelector = { it.next },
                    itemsSelector = { it.items },
                    fetchNextPage = {
                        called = true
                        error("must not be called")
                    },
                )

            assertEquals(429, response.statusCode)
            assertTrue(response.data is SpotifyResponseData.Error)
            assertEquals(false, called)
        }

    @Test
    fun collectAllItems_aggregatesSuccessPages() =
        runTest {
            val first =
                SpotifyApiResponse(
                    statusCode = 201,
                    data =
                        SpotifyResponseData.Success(
                            TestPage(
                                items = listOf(1, 2),
                                next = "https://api.spotify.com/v1/items?limit=2&offset=2",
                            ),
                        ),
                )
            var requestedOptions: PagingOptions? = null
            val response =
                PagingHelpers.collectAllItems(
                    firstPageResponse = first,
                    nextUrlSelector = { it.next },
                    itemsSelector = { it.items },
                    fetchNextPage = { options ->
                        requestedOptions = options
                        SpotifyApiResponse(
                            statusCode = 201,
                            data =
                                SpotifyResponseData.Success(
                                    TestPage(
                                        items = listOf(3, 4),
                                        next = null,
                                    ),
                                ),
                        )
                    },
                )

            val data = response.data as SpotifyResponseData.Success
            assertEquals(listOf(1, 2, 3, 4), data.value)
            assertEquals(2, requestedOptions?.limit)
            assertEquals(2, requestedOptions?.offset)
        }

    @Test
    fun collectAllItems_returnsError_whenLaterPageFails() =
        runTest {
            val first =
                SpotifyApiResponse(
                    statusCode = 201,
                    data =
                        SpotifyResponseData.Success(
                            TestPage(
                                items = listOf(1),
                                next = "https://api.spotify.com/v1/items?limit=1&offset=1",
                            ),
                        ),
                )
            val response =
                PagingHelpers.collectAllItems(
                    firstPageResponse = first,
                    nextUrlSelector = { it.next },
                    itemsSelector = { it.items },
                    fetchNextPage = {
                        SpotifyApiResponse(
                            statusCode = 429,
                            data =
                                SpotifyResponseData.Error(
                                    SpotifyErrorResponse(
                                        SpotifyError(429, "too many requests"),
                                    ),
                                ),
                        )
                    },
                )

            assertEquals(429, response.statusCode)
            assertTrue(response.data is SpotifyResponseData.Error)
        }

    @Test
    fun collectAllItems_withFetcherOverload_usesInitialPagingOptions() =
        runTest {
            var firstRequested: PagingOptions? = null
            val response =
                PagingHelpers.collectAllItems(
                    initialPagingOptions = PagingOptions(limit = 10, offset = 30),
                    fetchFirstPage = { options ->
                        firstRequested = options
                        SpotifyApiResponse(
                            statusCode = 201,
                            data = SpotifyResponseData.Success(TestPage(items = listOf(1), next = null)),
                        )
                    },
                    nextUrlSelector = { it.next },
                    itemsSelector = { it.items },
                    fetchNextPage = {
                        error("must not be called")
                    },
                )

            assertEquals(10, firstRequested?.limit)
            assertEquals(30, firstRequested?.offset)
            val data = response.data as SpotifyResponseData.Success
            assertEquals(listOf(1), data.value)
        }
}
