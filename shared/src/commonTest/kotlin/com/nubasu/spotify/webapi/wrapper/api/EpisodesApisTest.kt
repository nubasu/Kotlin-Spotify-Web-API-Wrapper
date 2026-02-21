package com.nubasu.spotify.webapi.wrapper.api

import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyResponseData
import com.nubasu.spotify.webapi.wrapper.api.episodes.EpisodesApis
import com.nubasu.spotify.webapi.wrapper.request.common.Ids
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EpisodesApisTest {
    @Test
    fun nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> EpisodesApis(client).getEpisode("id") }
    }

    @Test
    fun nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> EpisodesApis(client).getEpisode("id") }
    }

    @Test
    fun nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> EpisodesApis(client).getEpisode("id") }
    }

    @Test
    fun nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> EpisodesApis(client).getEpisode("id") }
    }

    @Test
    fun created201_returnsStatusAndBody() = runTest {
        val api = EpisodesApis(ApiTestClientFactory.successClient())
        val res = api.saveEpisodesForCurrentUser(Ids(listOf("e1")))
        assertEquals(201, res.statusCode)
        assertEquals(true, (res.data as SpotifyResponseData.Success).value)
    }
    @Test
    fun getEpisode_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> EpisodesApis(client).getEpisode("id") }
    }

    @Test
    fun getEpisode_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> EpisodesApis(client).getEpisode("id") }
    }

    @Test
    fun getEpisode_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> EpisodesApis(client).getEpisode("id") }
    }

    @Test
    fun getEpisode_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> EpisodesApis(client).getEpisode("id") }
    }
    @Test
    fun getSeveralEpisodes_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> EpisodesApis(client).getSeveralEpisodes(listOf("id1")) }
    }

    @Test
    fun getSeveralEpisodes_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> EpisodesApis(client).getSeveralEpisodes(listOf("id1")) }
    }

    @Test
    fun getSeveralEpisodes_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> EpisodesApis(client).getSeveralEpisodes(listOf("id1")) }
    }

    @Test
    fun getSeveralEpisodes_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> EpisodesApis(client).getSeveralEpisodes(listOf("id1")) }
    }
    @Test
    fun getUsersSavedEpisodes_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> EpisodesApis(client).getUsersSavedEpisodes() }
    }

    @Test
    fun getUsersSavedEpisodes_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> EpisodesApis(client).getUsersSavedEpisodes() }
    }

    @Test
    fun getUsersSavedEpisodes_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> EpisodesApis(client).getUsersSavedEpisodes() }
    }

    @Test
    fun getUsersSavedEpisodes_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> EpisodesApis(client).getUsersSavedEpisodes() }
    }
    @Test
    fun saveEpisodesForCurrentUser_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> EpisodesApis(client).saveEpisodesForCurrentUser(Ids(listOf("id1"))) }
    }

    @Test
    fun saveEpisodesForCurrentUser_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> EpisodesApis(client).saveEpisodesForCurrentUser(Ids(listOf("id1"))) }
    }

    @Test
    fun saveEpisodesForCurrentUser_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> EpisodesApis(client).saveEpisodesForCurrentUser(Ids(listOf("id1"))) }
    }

    @Test
    fun saveEpisodesForCurrentUser_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> EpisodesApis(client).saveEpisodesForCurrentUser(Ids(listOf("id1"))) }
    }
    @Test
    fun removeUsersSavedEpisodes_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> EpisodesApis(client).removeUsersSavedEpisodes(Ids(listOf("id1"))) }
    }

    @Test
    fun removeUsersSavedEpisodes_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> EpisodesApis(client).removeUsersSavedEpisodes(Ids(listOf("id1"))) }
    }

    @Test
    fun removeUsersSavedEpisodes_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> EpisodesApis(client).removeUsersSavedEpisodes(Ids(listOf("id1"))) }
    }

    @Test
    fun removeUsersSavedEpisodes_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> EpisodesApis(client).removeUsersSavedEpisodes(Ids(listOf("id1"))) }
    }
    @Test
    fun checkUsersSavedEpisodes_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> EpisodesApis(client).checkUsersSavedEpisodes(Ids(listOf("id1"))) }
    }

    @Test
    fun checkUsersSavedEpisodes_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> EpisodesApis(client).checkUsersSavedEpisodes(Ids(listOf("id1"))) }
    }

    @Test
    fun checkUsersSavedEpisodes_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> EpisodesApis(client).checkUsersSavedEpisodes(Ids(listOf("id1"))) }
    }

    @Test
    fun checkUsersSavedEpisodes_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> EpisodesApis(client).checkUsersSavedEpisodes(Ids(listOf("id1"))) }
    }
}
