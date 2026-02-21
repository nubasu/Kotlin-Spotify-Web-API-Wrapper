package com.nubasu.kotlin_spotify_web_api_wrapper.api

import com.nubasu.kotlin_spotify_web_api_wrapper.api.shows.ShowsApis
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.Ids
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ShowsApisTest {
    @Test
    fun nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> ShowsApis(client).getShow("show-id") }
    }

    @Test
    fun nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> ShowsApis(client).getShow("show-id") }
    }

    @Test
    fun nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> ShowsApis(client).getShow("show-id") }
    }

    @Test
    fun nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> ShowsApis(client).getShow("show-id") }
    }

    @Test
    fun created201_returnsStatusAndBody() = runTest {
        val api = ShowsApis(ApiTestClientFactory.successClient())
        val res = api.saveShowsForCurrentUser(Ids(listOf("s1")))
        assertEquals(201, res.statusCode)
        assertEquals(true, res.data)
    }
    @Test
    fun getShow_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> ShowsApis(client).getShow("show-id") }
    }

    @Test
    fun getShow_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> ShowsApis(client).getShow("show-id") }
    }

    @Test
    fun getShow_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> ShowsApis(client).getShow("show-id") }
    }

    @Test
    fun getShow_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> ShowsApis(client).getShow("show-id") }
    }
    @Test
    fun getSeveralShows_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> ShowsApis(client).getSeveralShows(listOf("s1")) }
    }

    @Test
    fun getSeveralShows_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> ShowsApis(client).getSeveralShows(listOf("s1")) }
    }

    @Test
    fun getSeveralShows_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> ShowsApis(client).getSeveralShows(listOf("s1")) }
    }

    @Test
    fun getSeveralShows_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> ShowsApis(client).getSeveralShows(listOf("s1")) }
    }
    @Test
    fun getShowEpisodes_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> ShowsApis(client).getShowEpisodes("show-id") }
    }

    @Test
    fun getShowEpisodes_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> ShowsApis(client).getShowEpisodes("show-id") }
    }

    @Test
    fun getShowEpisodes_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> ShowsApis(client).getShowEpisodes("show-id") }
    }

    @Test
    fun getShowEpisodes_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> ShowsApis(client).getShowEpisodes("show-id") }
    }
    @Test
    fun getUsersSavedShows_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> ShowsApis(client).getUsersSavedShows() }
    }

    @Test
    fun getUsersSavedShows_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> ShowsApis(client).getUsersSavedShows() }
    }

    @Test
    fun getUsersSavedShows_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> ShowsApis(client).getUsersSavedShows() }
    }

    @Test
    fun getUsersSavedShows_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> ShowsApis(client).getUsersSavedShows() }
    }
    @Test
    fun saveShowsForCurrentUser_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> ShowsApis(client).saveShowsForCurrentUser(Ids(listOf("s1"))) }
    }

    @Test
    fun saveShowsForCurrentUser_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> ShowsApis(client).saveShowsForCurrentUser(Ids(listOf("s1"))) }
    }

    @Test
    fun saveShowsForCurrentUser_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> ShowsApis(client).saveShowsForCurrentUser(Ids(listOf("s1"))) }
    }

    @Test
    fun saveShowsForCurrentUser_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> ShowsApis(client).saveShowsForCurrentUser(Ids(listOf("s1"))) }
    }
    @Test
    fun removeUsersSavedShows_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> ShowsApis(client).removeUsersSavedShows(Ids(listOf("s1"))) }
    }

    @Test
    fun removeUsersSavedShows_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> ShowsApis(client).removeUsersSavedShows(Ids(listOf("s1"))) }
    }

    @Test
    fun removeUsersSavedShows_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> ShowsApis(client).removeUsersSavedShows(Ids(listOf("s1"))) }
    }

    @Test
    fun removeUsersSavedShows_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> ShowsApis(client).removeUsersSavedShows(Ids(listOf("s1"))) }
    }
    @Test
    fun checkUsersSavedShows_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> ShowsApis(client).checkUsersSavedShows(Ids(listOf("s1"))) }
    }

    @Test
    fun checkUsersSavedShows_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> ShowsApis(client).checkUsersSavedShows(Ids(listOf("s1"))) }
    }

    @Test
    fun checkUsersSavedShows_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> ShowsApis(client).checkUsersSavedShows(Ids(listOf("s1"))) }
    }

    @Test
    fun checkUsersSavedShows_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> ShowsApis(client).checkUsersSavedShows(Ids(listOf("s1"))) }
    }
}
