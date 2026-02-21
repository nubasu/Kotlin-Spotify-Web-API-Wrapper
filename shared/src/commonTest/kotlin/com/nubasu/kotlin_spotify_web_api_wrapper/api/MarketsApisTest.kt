package com.nubasu.kotlin_spotify_web_api_wrapper.api

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyResponseData
import com.nubasu.kotlin_spotify_web_api_wrapper.api.markets.MarketsApis
import com.nubasu.kotlin_spotify_web_api_wrapper.api.fixtures.SpotifyApiFixtures
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class MarketsApisTest {
    @Test
    fun nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> MarketsApis(client).getAvailableMarkets() }
    }

    @Test
    fun nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> MarketsApis(client).getAvailableMarkets() }
    }

    @Test
    fun nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> MarketsApis(client).getAvailableMarkets() }
    }

    @Test
    fun nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> MarketsApis(client).getAvailableMarkets() }
    }

    @Test
    fun created201_returnsStatusAndBody() = runTest {
        val api = MarketsApis(ApiTestClientFactory.successClient(body = SpotifyApiFixtures.MARKETS_MIN))
        val res = api.getAvailableMarkets()
        assertEquals(201, res.statusCode)
        val data = (res.data as SpotifyResponseData.Success).value
        assertEquals(listOf("US"), data.markets)
    }

}
