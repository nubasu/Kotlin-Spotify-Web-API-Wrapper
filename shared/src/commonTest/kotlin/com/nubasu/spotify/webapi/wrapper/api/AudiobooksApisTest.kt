package com.nubasu.spotify.webapi.wrapper.api

import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyResponseData
import com.nubasu.spotify.webapi.wrapper.api.audiobooks.AudiobooksApis
import com.nubasu.spotify.webapi.wrapper.request.common.Ids
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AudiobooksApisTest {
    @Test
    fun nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> AudiobooksApis(client).getAnAudiobook("id") }
    }

    @Test
    fun nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> AudiobooksApis(client).getAnAudiobook("id") }
    }

    @Test
    fun nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> AudiobooksApis(client).getAnAudiobook("id") }
    }

    @Test
    fun nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> AudiobooksApis(client).getAnAudiobook("id") }
    }

    @Test
    fun created201_returnsStatusAndBody() = runTest {
        val api = AudiobooksApis(ApiTestClientFactory.successClient())
        val res = api.saveAudiobooksForCurrentUser(Ids(listOf("a")))
        assertEquals(201, res.statusCode)
        assertEquals(true, (res.data as SpotifyResponseData.Success).value)
    }
    @Test
    fun getAnAudiobook_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> AudiobooksApis(client).getAnAudiobook("id") }
    }

    @Test
    fun getAnAudiobook_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> AudiobooksApis(client).getAnAudiobook("id") }
    }

    @Test
    fun getAnAudiobook_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> AudiobooksApis(client).getAnAudiobook("id") }
    }

    @Test
    fun getAnAudiobook_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> AudiobooksApis(client).getAnAudiobook("id") }
    }
    @Test
    fun getSeveralAudiobooks_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> AudiobooksApis(client).getSeveralAudiobooks(listOf("id1", "id2")) }
    }

    @Test
    fun getSeveralAudiobooks_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> AudiobooksApis(client).getSeveralAudiobooks(listOf("id1", "id2")) }
    }

    @Test
    fun getSeveralAudiobooks_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> AudiobooksApis(client).getSeveralAudiobooks(listOf("id1", "id2")) }
    }

    @Test
    fun getSeveralAudiobooks_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> AudiobooksApis(client).getSeveralAudiobooks(listOf("id1", "id2")) }
    }
    @Test
    fun getAudiobookChapters_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> AudiobooksApis(client).getAudiobookChapters("id") }
    }

    @Test
    fun getAudiobookChapters_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> AudiobooksApis(client).getAudiobookChapters("id") }
    }

    @Test
    fun getAudiobookChapters_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> AudiobooksApis(client).getAudiobookChapters("id") }
    }

    @Test
    fun getAudiobookChapters_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> AudiobooksApis(client).getAudiobookChapters("id") }
    }
    @Test
    fun getUsersSavedAudiobooks_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> AudiobooksApis(client).getUsersSavedAudiobooks() }
    }

    @Test
    fun getUsersSavedAudiobooks_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> AudiobooksApis(client).getUsersSavedAudiobooks() }
    }

    @Test
    fun getUsersSavedAudiobooks_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> AudiobooksApis(client).getUsersSavedAudiobooks() }
    }

    @Test
    fun getUsersSavedAudiobooks_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> AudiobooksApis(client).getUsersSavedAudiobooks() }
    }
    @Test
    fun saveAudiobooksForCurrentUser_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> AudiobooksApis(client).saveAudiobooksForCurrentUser(Ids(listOf("id1"))) }
    }

    @Test
    fun saveAudiobooksForCurrentUser_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> AudiobooksApis(client).saveAudiobooksForCurrentUser(Ids(listOf("id1"))) }
    }

    @Test
    fun saveAudiobooksForCurrentUser_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> AudiobooksApis(client).saveAudiobooksForCurrentUser(Ids(listOf("id1"))) }
    }

    @Test
    fun saveAudiobooksForCurrentUser_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> AudiobooksApis(client).saveAudiobooksForCurrentUser(Ids(listOf("id1"))) }
    }
    @Test
    fun removeUsersSavedAudiobooks_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> AudiobooksApis(client).removeUsersSavedAudiobooks(Ids(listOf("id1"))) }
    }

    @Test
    fun removeUsersSavedAudiobooks_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> AudiobooksApis(client).removeUsersSavedAudiobooks(Ids(listOf("id1"))) }
    }

    @Test
    fun removeUsersSavedAudiobooks_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> AudiobooksApis(client).removeUsersSavedAudiobooks(Ids(listOf("id1"))) }
    }

    @Test
    fun removeUsersSavedAudiobooks_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> AudiobooksApis(client).removeUsersSavedAudiobooks(Ids(listOf("id1"))) }
    }
    @Test
    fun checkUsersSavedAudiobooks_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> AudiobooksApis(client).checkUsersSavedAudiobooks(Ids(listOf("id1"))) }
    }

    @Test
    fun checkUsersSavedAudiobooks_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> AudiobooksApis(client).checkUsersSavedAudiobooks(Ids(listOf("id1"))) }
    }

    @Test
    fun checkUsersSavedAudiobooks_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> AudiobooksApis(client).checkUsersSavedAudiobooks(Ids(listOf("id1"))) }
    }

    @Test
    fun checkUsersSavedAudiobooks_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> AudiobooksApis(client).checkUsersSavedAudiobooks(Ids(listOf("id1"))) }
    }
}
