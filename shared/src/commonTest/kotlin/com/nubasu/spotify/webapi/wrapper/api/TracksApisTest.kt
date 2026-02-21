package com.nubasu.spotify.webapi.wrapper.api

import com.nubasu.spotify.webapi.wrapper.api.fixtures.SpotifyApiFixtures
import com.nubasu.spotify.webapi.wrapper.api.tracks.TracksApis
import com.nubasu.spotify.webapi.wrapper.request.common.Ids
import com.nubasu.spotify.webapi.wrapper.request.tracks.RecommendationSeeds
import com.nubasu.spotify.webapi.wrapper.request.tracks.RecommendationTunableAttributes
import com.nubasu.spotify.webapi.wrapper.request.tracks.SaveTracksForCurrentUserRequest
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyResponseData
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TracksApisTest {
    @Test
    fun nonSuccess_throws_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created { client -> TracksApis(client).getTrack("track-id") }
        }

    @Test
    fun nonSuccess_throws_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> TracksApis(client).getTrack("track-id") }
        }

    @Test
    fun nonSuccess_throws_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client -> TracksApis(client).getTrack("track-id") }
        }

    @Test
    fun nonSuccess_throws_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> TracksApis(client).getTrack("track-id") }
        }

    @Test
    fun created201_returnsStatusAndBody() =
        runTest {
            val api = TracksApis(ApiTestClientFactory.successClient())
            val res = api.saveTracksForCurrentUser(SaveTracksForCurrentUserRequest(ids = listOf("t1")))
            assertEquals(201, res.statusCode)
            assertEquals(true, (res.data as SpotifyResponseData.Success).value)
        }

    @Test
    fun getTrack_nonSuccess_throws_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created { client -> TracksApis(client).getTrack("track-id") }
        }

    @Test
    fun getTrack_nonSuccess_throws_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> TracksApis(client).getTrack("track-id") }
        }

    @Test
    fun getTrack_nonSuccess_throws_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client -> TracksApis(client).getTrack("track-id") }
        }

    @Test
    fun getTrack_nonSuccess_throws_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> TracksApis(client).getTrack("track-id") }
        }

    @Test
    fun getSeveralTracks_nonSuccess_throws_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created { client -> TracksApis(client).getSeveralTracks(listOf("t1")) }
        }

    @Test
    fun getSeveralTracks_nonSuccess_throws_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> TracksApis(client).getSeveralTracks(listOf("t1")) }
        }

    @Test
    fun getSeveralTracks_nonSuccess_throws_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client -> TracksApis(client).getSeveralTracks(listOf("t1")) }
        }

    @Test
    fun getSeveralTracks_nonSuccess_throws_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> TracksApis(client).getSeveralTracks(listOf("t1")) }
        }

    @Test
    fun getUsersSavedTracks_nonSuccess_throws_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created { client -> TracksApis(client).getUsersSavedTracks() }
        }

    @Test
    fun getUsersSavedTracks_nonSuccess_throws_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> TracksApis(client).getUsersSavedTracks() }
        }

    @Test
    fun getUsersSavedTracks_nonSuccess_throws_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client -> TracksApis(client).getUsersSavedTracks() }
        }

    @Test
    fun getUsersSavedTracks_nonSuccess_throws_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> TracksApis(client).getUsersSavedTracks() }
        }

    @Test
    fun saveTracksForCurrentUser_nonSuccess_throws_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created { client ->
                TracksApis(client).saveTracksForCurrentUser(SaveTracksForCurrentUserRequest(ids = listOf("t1")))
            }
        }

    @Test
    fun saveTracksForCurrentUser_nonSuccess_throws_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client ->
                TracksApis(client).saveTracksForCurrentUser(SaveTracksForCurrentUserRequest(ids = listOf("t1")))
            }
        }

    @Test
    fun saveTracksForCurrentUser_nonSuccess_throws_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client ->
                TracksApis(client).saveTracksForCurrentUser(SaveTracksForCurrentUserRequest(ids = listOf("t1")))
            }
        }

    @Test
    fun saveTracksForCurrentUser_nonSuccess_throws_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client ->
                TracksApis(client).saveTracksForCurrentUser(SaveTracksForCurrentUserRequest(ids = listOf("t1")))
            }
        }

    @Test
    fun removeUsersSavedTracks_nonSuccess_throws_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created { client -> TracksApis(client).removeUsersSavedTracks(Ids(listOf("t1"))) }
        }

    @Test
    fun removeUsersSavedTracks_nonSuccess_throws_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> TracksApis(client).removeUsersSavedTracks(Ids(listOf("t1"))) }
        }

    @Test
    fun removeUsersSavedTracks_nonSuccess_throws_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client -> TracksApis(client).removeUsersSavedTracks(Ids(listOf("t1"))) }
        }

    @Test
    fun removeUsersSavedTracks_nonSuccess_throws_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> TracksApis(client).removeUsersSavedTracks(Ids(listOf("t1"))) }
        }

    @Test
    fun checkUsersSavedTracks_nonSuccess_throws_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created { client -> TracksApis(client).checkUsersSavedTracks(Ids(listOf("t1"))) }
        }

    @Test
    fun checkUsersSavedTracks_nonSuccess_throws_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> TracksApis(client).checkUsersSavedTracks(Ids(listOf("t1"))) }
        }

    @Test
    fun checkUsersSavedTracks_nonSuccess_throws_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client -> TracksApis(client).checkUsersSavedTracks(Ids(listOf("t1"))) }
        }

    @Test
    fun checkUsersSavedTracks_nonSuccess_throws_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> TracksApis(client).checkUsersSavedTracks(Ids(listOf("t1"))) }
        }

    @Test
    fun getTracksAudioFeatures_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created(
                successBody = SpotifyApiFixtures.TRACK_AUDIO_FEATURES_ONE_MIN,
                assertData = {
                    assertEquals("t1", it.id)
                    assertEquals(200000, it.durationMs)
                },
            ) { client ->
                TracksApis(client).getTracksAudioFeatures("t1")
            }
        }

    @Test
    fun getTracksAudioFeatures_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> TracksApis(client).getTracksAudioFeatures("t1") }
        }

    @Test
    fun getTracksAudioFeatures_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client -> TracksApis(client).getTracksAudioFeatures("t1") }
        }

    @Test
    fun getTracksAudioFeatures_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> TracksApis(client).getTracksAudioFeatures("t1") }
        }

    @Test
    fun getSeveralTracksAudioFeatures_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created(
                successBody = SpotifyApiFixtures.TRACK_AUDIO_FEATURES_SEVERAL_MIN,
                assertData = {
                    assertEquals(1, it.audioFeatures.size)
                    assertEquals("t1", it.audioFeatures.first().id)
                },
            ) { client ->
                TracksApis(client).getSeveralTracksAudioFeatures(listOf("t1"))
            }
        }

    @Test
    fun getSeveralTracksAudioFeatures_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> TracksApis(client).getSeveralTracksAudioFeatures(listOf("t1")) }
        }

    @Test
    fun getSeveralTracksAudioFeatures_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client -> TracksApis(client).getSeveralTracksAudioFeatures(listOf("t1")) }
        }

    @Test
    fun getSeveralTracksAudioFeatures_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> TracksApis(client).getSeveralTracksAudioFeatures(listOf("t1")) }
        }

    @Test
    fun getTracksAudioAnalysis_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created(
                successBody = SpotifyApiFixtures.TRACK_AUDIO_ANALYSIS_MIN,
                assertData = {
                    assertEquals(1, it.bars.size)
                    assertEquals(1, it.tatums.size)
                },
            ) { client ->
                TracksApis(client).getTracksAudioAnalysis("t1")
            }
        }

    @Test
    fun getTracksAudioAnalysis_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> TracksApis(client).getTracksAudioAnalysis("t1") }
        }

    @Test
    fun getTracksAudioAnalysis_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client -> TracksApis(client).getTracksAudioAnalysis("t1") }
        }

    @Test
    fun getTracksAudioAnalysis_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> TracksApis(client).getTracksAudioAnalysis("t1") }
        }

    @Test
    fun getRecommendations_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created(
                successBody = SpotifyApiFixtures.RECOMMENDATIONS_MIN,
                assertData = {
                    assertEquals(1, it.seeds.size)
                    assertEquals(1, it.tracks.size)
                },
            ) { client ->
                TracksApis(client).getRecommendations(
                    seeds = RecommendationSeeds(genres = listOf("pop")),
                    tunable = RecommendationTunableAttributes(targetEnergy = 0.7),
                )
            }
        }

    @Test
    fun getRecommendations_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client ->
                TracksApis(client).getRecommendations(seeds = RecommendationSeeds(genres = listOf("pop")))
            }
        }

    @Test
    fun getRecommendations_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client ->
                TracksApis(client).getRecommendations(seeds = RecommendationSeeds(genres = listOf("pop")))
            }
        }

    @Test
    fun getRecommendations_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client ->
                TracksApis(client).getRecommendations(seeds = RecommendationSeeds(genres = listOf("pop")))
            }
        }
}
