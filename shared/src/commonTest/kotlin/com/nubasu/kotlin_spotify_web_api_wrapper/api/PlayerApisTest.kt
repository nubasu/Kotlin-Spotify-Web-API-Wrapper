package com.nubasu.kotlin_spotify_web_api_wrapper.api

import com.nubasu.kotlin_spotify_web_api_wrapper.api.player.PlayerApis
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.Uris
import com.nubasu.kotlin_spotify_web_api_wrapper.request.player.DeviceIds
import com.nubasu.kotlin_spotify_web_api_wrapper.request.player.RepeatMode
import com.nubasu.kotlin_spotify_web_api_wrapper.request.player.State
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PlayerApisTest {
    @Test
    fun nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlayerApis(client).getPlaybackState() }
    }

    @Test
    fun nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlayerApis(client).getPlaybackState() }
    }

    @Test
    fun nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlayerApis(client).getPlaybackState() }
    }

    @Test
    fun nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlayerApis(client).getPlaybackState() }
    }

    @Test
    fun created201_returnsStatusAndBody() = runTest {
        val api = PlayerApis(ApiTestClientFactory.successClient())
        val res = api.pausePlayback()
        assertEquals(201, res.statusCode)
        assertEquals(true, res.data)
    }
    @Test
    fun getPlaybackState_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlayerApis(client).getPlaybackState() }
    }

    @Test
    fun getPlaybackState_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlayerApis(client).getPlaybackState() }
    }

    @Test
    fun getPlaybackState_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlayerApis(client).getPlaybackState() }
    }

    @Test
    fun getPlaybackState_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlayerApis(client).getPlaybackState() }
    }
    @Test
    fun transferPlayback_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlayerApis(client).transferPlayback(DeviceIds(listOf("d1"))) }
    }

    @Test
    fun transferPlayback_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlayerApis(client).transferPlayback(DeviceIds(listOf("d1"))) }
    }

    @Test
    fun transferPlayback_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlayerApis(client).transferPlayback(DeviceIds(listOf("d1"))) }
    }

    @Test
    fun transferPlayback_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlayerApis(client).transferPlayback(DeviceIds(listOf("d1"))) }
    }
    @Test
    fun getAvailableDevices_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlayerApis(client).getAvailableDevices() }
    }

    @Test
    fun getAvailableDevices_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlayerApis(client).getAvailableDevices() }
    }

    @Test
    fun getAvailableDevices_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlayerApis(client).getAvailableDevices() }
    }

    @Test
    fun getAvailableDevices_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlayerApis(client).getAvailableDevices() }
    }
    @Test
    fun getCurrentlyPlayingTrack_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlayerApis(client).getCurrentlyPlayingTrack() }
    }

    @Test
    fun getCurrentlyPlayingTrack_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlayerApis(client).getCurrentlyPlayingTrack() }
    }

    @Test
    fun getCurrentlyPlayingTrack_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlayerApis(client).getCurrentlyPlayingTrack() }
    }

    @Test
    fun getCurrentlyPlayingTrack_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlayerApis(client).getCurrentlyPlayingTrack() }
    }
    @Test
    fun startResumePlayback_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlayerApis(client).startResumePlayback(uris = Uris(listOf("spotify:track:1"))) }
    }

    @Test
    fun startResumePlayback_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlayerApis(client).startResumePlayback(uris = Uris(listOf("spotify:track:1"))) }
    }

    @Test
    fun startResumePlayback_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlayerApis(client).startResumePlayback(uris = Uris(listOf("spotify:track:1"))) }
    }

    @Test
    fun startResumePlayback_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlayerApis(client).startResumePlayback(uris = Uris(listOf("spotify:track:1"))) }
    }
    @Test
    fun pausePlayback_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlayerApis(client).pausePlayback() }
    }

    @Test
    fun pausePlayback_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlayerApis(client).pausePlayback() }
    }

    @Test
    fun pausePlayback_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlayerApis(client).pausePlayback() }
    }

    @Test
    fun pausePlayback_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlayerApis(client).pausePlayback() }
    }
    @Test
    fun skipToNext_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlayerApis(client).skipToNext() }
    }

    @Test
    fun skipToNext_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlayerApis(client).skipToNext() }
    }

    @Test
    fun skipToNext_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlayerApis(client).skipToNext() }
    }

    @Test
    fun skipToNext_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlayerApis(client).skipToNext() }
    }
    @Test
    fun skipToPrevious_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlayerApis(client).skipToPrevious() }
    }

    @Test
    fun skipToPrevious_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlayerApis(client).skipToPrevious() }
    }

    @Test
    fun skipToPrevious_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlayerApis(client).skipToPrevious() }
    }

    @Test
    fun skipToPrevious_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlayerApis(client).skipToPrevious() }
    }
    @Test
    fun seekToPosition_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlayerApis(client).seekToPosition(1000) }
    }

    @Test
    fun seekToPosition_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlayerApis(client).seekToPosition(1000) }
    }

    @Test
    fun seekToPosition_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlayerApis(client).seekToPosition(1000) }
    }

    @Test
    fun seekToPosition_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlayerApis(client).seekToPosition(1000) }
    }
    @Test
    fun setRepeatMode_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlayerApis(client).setRepeatMode(State(RepeatMode.OFF)) }
    }

    @Test
    fun setRepeatMode_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlayerApis(client).setRepeatMode(State(RepeatMode.OFF)) }
    }

    @Test
    fun setRepeatMode_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlayerApis(client).setRepeatMode(State(RepeatMode.OFF)) }
    }

    @Test
    fun setRepeatMode_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlayerApis(client).setRepeatMode(State(RepeatMode.OFF)) }
    }
    @Test
    fun setPlaybackVolume_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlayerApis(client).setPlaybackVolume(50) }
    }

    @Test
    fun setPlaybackVolume_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlayerApis(client).setPlaybackVolume(50) }
    }

    @Test
    fun setPlaybackVolume_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlayerApis(client).setPlaybackVolume(50) }
    }

    @Test
    fun setPlaybackVolume_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlayerApis(client).setPlaybackVolume(50) }
    }
    @Test
    fun togglePlaybackShuffle_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlayerApis(client).togglePlaybackShuffle(true) }
    }

    @Test
    fun togglePlaybackShuffle_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlayerApis(client).togglePlaybackShuffle(true) }
    }

    @Test
    fun togglePlaybackShuffle_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlayerApis(client).togglePlaybackShuffle(true) }
    }

    @Test
    fun togglePlaybackShuffle_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlayerApis(client).togglePlaybackShuffle(true) }
    }
    @Test
    fun getRecentlyPlayedTracks_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlayerApis(client).getRecentlyPlayedTracks() }
    }

    @Test
    fun getRecentlyPlayedTracks_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlayerApis(client).getRecentlyPlayedTracks() }
    }

    @Test
    fun getRecentlyPlayedTracks_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlayerApis(client).getRecentlyPlayedTracks() }
    }

    @Test
    fun getRecentlyPlayedTracks_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlayerApis(client).getRecentlyPlayedTracks() }
    }
    @Test
    fun getTheUsersQueue_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlayerApis(client).getTheUsersQueue() }
    }

    @Test
    fun getTheUsersQueue_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlayerApis(client).getTheUsersQueue() }
    }

    @Test
    fun getTheUsersQueue_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlayerApis(client).getTheUsersQueue() }
    }

    @Test
    fun getTheUsersQueue_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlayerApis(client).getTheUsersQueue() }
    }
    @Test
    fun addItemToPlaybackQueue_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlayerApis(client).addItemToPlaybackQueue("spotify:track:1") }
    }

    @Test
    fun addItemToPlaybackQueue_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlayerApis(client).addItemToPlaybackQueue("spotify:track:1") }
    }

    @Test
    fun addItemToPlaybackQueue_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlayerApis(client).addItemToPlaybackQueue("spotify:track:1") }
    }

    @Test
    fun addItemToPlaybackQueue_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlayerApis(client).addItemToPlaybackQueue("spotify:track:1") }
    }
}
