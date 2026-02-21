package com.nubasu.kotlin_spotify_web_api_wrapper.api

import com.nubasu.kotlin_spotify_web_api_wrapper.api.search.SearchApis
import com.nubasu.kotlin_spotify_web_api_wrapper.api.fixtures.SpotifyApiFixtures
import com.nubasu.kotlin_spotify_web_api_wrapper.request.search.SearchType
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SearchApisTest {
    @Test
    fun nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> SearchApis(client).searchForItem("test", setOf(SearchType.TRACK)) }
    }

    @Test
    fun nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> SearchApis(client).searchForItem("test", setOf(SearchType.TRACK)) }
    }

    @Test
    fun nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> SearchApis(client).searchForItem("test", setOf(SearchType.TRACK)) }
    }

    @Test
    fun nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> SearchApis(client).searchForItem("test", setOf(SearchType.TRACK)) }
    }

    @Test
    fun created201_returnsStatusAndBody() = runTest {
        val api = SearchApis(ApiTestClientFactory.successClient(body = SpotifyApiFixtures.SEARCH_MIN))
        val res = api.searchForItem("test", setOf(SearchType.TRACK))
        assertEquals(201, res.statusCode)
        assertEquals(null, res.data.tracks)
    }

}
