package com.nubasu.kotlin_spotify_web_api_wrapper.api

import com.nubasu.kotlin_spotify_web_api_wrapper.api.tracks.TracksApis
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.Ids
import com.nubasu.kotlin_spotify_web_api_wrapper.request.tracks.SaveTracksForCurrentUserRequest
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TracksApisTest {
    @Test
    fun nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> TracksApis(client).getTrack("track-id") }
    }

    @Test
    fun nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> TracksApis(client).getTrack("track-id") }
    }

    @Test
    fun nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> TracksApis(client).getTrack("track-id") }
    }

    @Test
    fun nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> TracksApis(client).getTrack("track-id") }
    }

    @Test
    fun created201_returnsStatusAndBody() = runTest {
        val api = TracksApis(ApiTestClientFactory.successClient())
        val res = api.saveTracksForCurrentUser(SaveTracksForCurrentUserRequest(ids = listOf("t1")))
        assertEquals(201, res.statusCode)
        assertEquals(true, res.data)
    }
    @Test
    fun getTrack_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> TracksApis(client).getTrack("track-id") }
    }

    @Test
    fun getTrack_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> TracksApis(client).getTrack("track-id") }
    }

    @Test
    fun getTrack_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> TracksApis(client).getTrack("track-id") }
    }

    @Test
    fun getTrack_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> TracksApis(client).getTrack("track-id") }
    }
    @Test
    fun getSeveralTracks_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> TracksApis(client).getSeveralTracks(listOf("t1")) }
    }

    @Test
    fun getSeveralTracks_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> TracksApis(client).getSeveralTracks(listOf("t1")) }
    }

    @Test
    fun getSeveralTracks_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> TracksApis(client).getSeveralTracks(listOf("t1")) }
    }

    @Test
    fun getSeveralTracks_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> TracksApis(client).getSeveralTracks(listOf("t1")) }
    }
    @Test
    fun getUsersSavedTracks_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> TracksApis(client).getUsersSavedTracks() }
    }

    @Test
    fun getUsersSavedTracks_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> TracksApis(client).getUsersSavedTracks() }
    }

    @Test
    fun getUsersSavedTracks_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> TracksApis(client).getUsersSavedTracks() }
    }

    @Test
    fun getUsersSavedTracks_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> TracksApis(client).getUsersSavedTracks() }
    }
    @Test
    fun saveTracksForCurrentUser_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> TracksApis(client).saveTracksForCurrentUser(SaveTracksForCurrentUserRequest(ids = listOf("t1"))) }
    }

    @Test
    fun saveTracksForCurrentUser_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> TracksApis(client).saveTracksForCurrentUser(SaveTracksForCurrentUserRequest(ids = listOf("t1"))) }
    }

    @Test
    fun saveTracksForCurrentUser_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> TracksApis(client).saveTracksForCurrentUser(SaveTracksForCurrentUserRequest(ids = listOf("t1"))) }
    }

    @Test
    fun saveTracksForCurrentUser_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> TracksApis(client).saveTracksForCurrentUser(SaveTracksForCurrentUserRequest(ids = listOf("t1"))) }
    }
    @Test
    fun removeUsersSavedTracks_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> TracksApis(client).removeUsersSavedTracks(Ids(listOf("t1"))) }
    }

    @Test
    fun removeUsersSavedTracks_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> TracksApis(client).removeUsersSavedTracks(Ids(listOf("t1"))) }
    }

    @Test
    fun removeUsersSavedTracks_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> TracksApis(client).removeUsersSavedTracks(Ids(listOf("t1"))) }
    }

    @Test
    fun removeUsersSavedTracks_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> TracksApis(client).removeUsersSavedTracks(Ids(listOf("t1"))) }
    }
    @Test
    fun checkUsersSavedTracks_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> TracksApis(client).checkUsersSavedTracks(Ids(listOf("t1"))) }
    }

    @Test
    fun checkUsersSavedTracks_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> TracksApis(client).checkUsersSavedTracks(Ids(listOf("t1"))) }
    }

    @Test
    fun checkUsersSavedTracks_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> TracksApis(client).checkUsersSavedTracks(Ids(listOf("t1"))) }
    }

    @Test
    fun checkUsersSavedTracks_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> TracksApis(client).checkUsersSavedTracks(Ids(listOf("t1"))) }
    }
}
