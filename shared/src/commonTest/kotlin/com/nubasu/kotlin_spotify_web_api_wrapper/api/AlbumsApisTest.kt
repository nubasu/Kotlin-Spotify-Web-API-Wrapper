package com.nubasu.kotlin_spotify_web_api_wrapper.api

import com.nubasu.kotlin_spotify_web_api_wrapper.api.albums.AlbumsApis
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.Ids
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AlbumsApisTest {
    @Test
    fun nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> AlbumsApis(client).getAlbum("id") }
    }

    @Test
    fun nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> AlbumsApis(client).getAlbum("id") }
    }

    @Test
    fun nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> AlbumsApis(client).getAlbum("id") }
    }

    @Test
    fun nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> AlbumsApis(client).getAlbum("id") }
    }

    @Test
    fun created201_returnsStatusAndBody() = runTest {
        val api = AlbumsApis(ApiTestClientFactory.successClient())
        val res = api.saveAlbumsForCurrentUser(Ids(listOf("a", "b")))
        assertEquals(201, res.statusCode)
        assertEquals(true, res.data)
    }
    @Test
    fun getAlbum_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> AlbumsApis(client).getAlbum("id") }
    }

    @Test
    fun getAlbum_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> AlbumsApis(client).getAlbum("id") }
    }

    @Test
    fun getAlbum_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> AlbumsApis(client).getAlbum("id") }
    }

    @Test
    fun getAlbum_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> AlbumsApis(client).getAlbum("id") }
    }
    @Test
    fun getSeveralAlbums_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> AlbumsApis(client).getSeveralAlbums(listOf("id1", "id2")) }
    }

    @Test
    fun getSeveralAlbums_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> AlbumsApis(client).getSeveralAlbums(listOf("id1", "id2")) }
    }

    @Test
    fun getSeveralAlbums_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> AlbumsApis(client).getSeveralAlbums(listOf("id1", "id2")) }
    }

    @Test
    fun getSeveralAlbums_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> AlbumsApis(client).getSeveralAlbums(listOf("id1", "id2")) }
    }
    @Test
    fun getAlbumTracks_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> AlbumsApis(client).getAlbumTracks("id") }
    }

    @Test
    fun getAlbumTracks_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> AlbumsApis(client).getAlbumTracks("id") }
    }

    @Test
    fun getAlbumTracks_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> AlbumsApis(client).getAlbumTracks("id") }
    }

    @Test
    fun getAlbumTracks_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> AlbumsApis(client).getAlbumTracks("id") }
    }
    @Test
    fun getUsersSavedAlbums_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> AlbumsApis(client).getUsersSavedAlbums() }
    }

    @Test
    fun getUsersSavedAlbums_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> AlbumsApis(client).getUsersSavedAlbums() }
    }

    @Test
    fun getUsersSavedAlbums_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> AlbumsApis(client).getUsersSavedAlbums() }
    }

    @Test
    fun getUsersSavedAlbums_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> AlbumsApis(client).getUsersSavedAlbums() }
    }
    @Test
    fun saveAlbumsForCurrentUser_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> AlbumsApis(client).saveAlbumsForCurrentUser(Ids(listOf("id1"))) }
    }

    @Test
    fun saveAlbumsForCurrentUser_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> AlbumsApis(client).saveAlbumsForCurrentUser(Ids(listOf("id1"))) }
    }

    @Test
    fun saveAlbumsForCurrentUser_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> AlbumsApis(client).saveAlbumsForCurrentUser(Ids(listOf("id1"))) }
    }

    @Test
    fun saveAlbumsForCurrentUser_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> AlbumsApis(client).saveAlbumsForCurrentUser(Ids(listOf("id1"))) }
    }
    @Test
    fun removeUsersSavedAlbums_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> AlbumsApis(client).removeUsersSavedAlbums(Ids(listOf("id1"))) }
    }

    @Test
    fun removeUsersSavedAlbums_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> AlbumsApis(client).removeUsersSavedAlbums(Ids(listOf("id1"))) }
    }

    @Test
    fun removeUsersSavedAlbums_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> AlbumsApis(client).removeUsersSavedAlbums(Ids(listOf("id1"))) }
    }

    @Test
    fun removeUsersSavedAlbums_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> AlbumsApis(client).removeUsersSavedAlbums(Ids(listOf("id1"))) }
    }
    @Test
    fun checkUsersSavedAlbums_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> AlbumsApis(client).checkUsersSavedAlbums(Ids(listOf("id1"))) }
    }

    @Test
    fun checkUsersSavedAlbums_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> AlbumsApis(client).checkUsersSavedAlbums(Ids(listOf("id1"))) }
    }

    @Test
    fun checkUsersSavedAlbums_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> AlbumsApis(client).checkUsersSavedAlbums(Ids(listOf("id1"))) }
    }

    @Test
    fun checkUsersSavedAlbums_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> AlbumsApis(client).checkUsersSavedAlbums(Ids(listOf("id1"))) }
    }
    @Test
    fun getNewReleases_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> AlbumsApis(client).getNewReleases() }
    }

    @Test
    fun getNewReleases_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> AlbumsApis(client).getNewReleases() }
    }

    @Test
    fun getNewReleases_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> AlbumsApis(client).getNewReleases() }
    }

    @Test
    fun getNewReleases_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> AlbumsApis(client).getNewReleases() }
    }
}
