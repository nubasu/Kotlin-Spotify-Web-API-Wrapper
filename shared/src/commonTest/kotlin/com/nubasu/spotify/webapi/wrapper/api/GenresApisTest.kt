@file:Suppress("DEPRECATION")

package com.nubasu.spotify.webapi.wrapper.api

import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyResponseData
import com.nubasu.spotify.webapi.wrapper.api.genres.GenresApis
import com.nubasu.spotify.webapi.wrapper.api.fixtures.SpotifyApiFixtures
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GenresApisTest {
    @Suppress("DEPRECATION")
    @Test
    fun nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> GenresApis(client).getAvailableGenreSeeds() }
    }

    @Test
    fun nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> GenresApis(client).getAvailableGenreSeeds() }
    }

    @Test
    fun nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> GenresApis(client).getAvailableGenreSeeds() }
    }

    @Test
    fun nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> GenresApis(client).getAvailableGenreSeeds() }
    }

    @Suppress("DEPRECATION")
    @Test
    fun created201_returnsStatusAndBody() = runTest {
        val api = GenresApis(ApiTestClientFactory.successClient(body = SpotifyApiFixtures.GENRES_MIN))
        val res = api.getAvailableGenreSeeds()
        assertEquals(201, res.statusCode)
        val data = (res.data as SpotifyResponseData.Success).value
        assertEquals(listOf("pop"), data.genres)
    }

}
