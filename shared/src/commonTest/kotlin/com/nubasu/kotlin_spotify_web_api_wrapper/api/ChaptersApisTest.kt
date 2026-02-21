package com.nubasu.kotlin_spotify_web_api_wrapper.api

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyResponseData
import com.nubasu.kotlin_spotify_web_api_wrapper.api.chapters.ChaptersApis
import com.nubasu.kotlin_spotify_web_api_wrapper.api.fixtures.SpotifyApiFixtures
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ChaptersApisTest {
    @Test
    fun nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> ChaptersApis(client).getAChapter("id") }
    }

    @Test
    fun nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> ChaptersApis(client).getAChapter("id") }
    }

    @Test
    fun nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> ChaptersApis(client).getAChapter("id") }
    }

    @Test
    fun nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> ChaptersApis(client).getAChapter("id") }
    }

    @Test
    fun created201_returnsStatusAndBody() = runTest {
        val api = ChaptersApis(ApiTestClientFactory.successClient(body = SpotifyApiFixtures.CHAPTERS_MIN))
        val res = api.getSeveralChapters(listOf("c1"))
        assertEquals(201, res.statusCode)
        val data = (res.data as SpotifyResponseData.Success).value
        assertEquals(0, data.chapters.size)
    }
    @Test
    fun getAChapter_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> ChaptersApis(client).getAChapter("id") }
    }

    @Test
    fun getAChapter_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> ChaptersApis(client).getAChapter("id") }
    }

    @Test
    fun getAChapter_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> ChaptersApis(client).getAChapter("id") }
    }

    @Test
    fun getAChapter_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> ChaptersApis(client).getAChapter("id") }
    }
    @Test
    fun getSeveralChapters_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> ChaptersApis(client).getSeveralChapters(listOf("id1")) }
    }

    @Test
    fun getSeveralChapters_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> ChaptersApis(client).getSeveralChapters(listOf("id1")) }
    }

    @Test
    fun getSeveralChapters_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> ChaptersApis(client).getSeveralChapters(listOf("id1")) }
    }

    @Test
    fun getSeveralChapters_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> ChaptersApis(client).getSeveralChapters(listOf("id1")) }
    }
}
