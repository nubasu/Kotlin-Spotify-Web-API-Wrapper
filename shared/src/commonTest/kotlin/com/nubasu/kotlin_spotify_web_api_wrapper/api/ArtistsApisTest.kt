@file:Suppress("DEPRECATION")

package com.nubasu.kotlin_spotify_web_api_wrapper.api

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyResponseData
import com.nubasu.kotlin_spotify_web_api_wrapper.api.artists.ArtistsApis
import com.nubasu.kotlin_spotify_web_api_wrapper.api.fixtures.SpotifyApiFixtures
import com.nubasu.kotlin_spotify_web_api_wrapper.request.common.IncludeGroup
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertFailsWith

class ArtistsApisTest {
    @Test
    fun nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> ArtistsApis(client).getArtist("id") }
    }

    @Test
    fun nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> ArtistsApis(client).getArtist("id") }
    }

    @Test
    fun nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> ArtistsApis(client).getArtist("id") }
    }

    @Test
    fun nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> ArtistsApis(client).getArtist("id") }
    }

    @Test
    fun created201_returnsStatusAndBody() = runTest {
        val api = ArtistsApis(ApiTestClientFactory.successClient(body = SpotifyApiFixtures.ARTIST_MIN))
        val res = api.getArtist("id")
        assertEquals(201, res.statusCode)
        val data = (res.data as SpotifyResponseData.Success).value
    }

    @Suppress("DEPRECATION")
    @Test
    fun getArtist_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> ArtistsApis(client).getArtist("id") }
    }

    @Test
    fun getArtist_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> ArtistsApis(client).getArtist("id") }
    }

    @Test
    fun getArtist_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> ArtistsApis(client).getArtist("id") }
    }

    @Test
    fun getArtist_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> ArtistsApis(client).getArtist("id") }
    }
    @Test
    fun getSeveralArtists_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> ArtistsApis(client).getSeveralArtists(listOf("a", "b")) }
    }

    @Test
    fun getSeveralArtists_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> ArtistsApis(client).getSeveralArtists(listOf("a", "b")) }
    }

    @Test
    fun getSeveralArtists_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> ArtistsApis(client).getSeveralArtists(listOf("a", "b")) }
    }

    @Test
    fun getSeveralArtists_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> ArtistsApis(client).getSeveralArtists(listOf("a", "b")) }
    }
    @Test
    fun getArtistsAlbums_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> ArtistsApis(client).getArtistsAlbums("id", includeGroups = listOf(IncludeGroup.Album)) }
    }

    @Test
    fun getArtistsAlbums_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> ArtistsApis(client).getArtistsAlbums("id", includeGroups = listOf(IncludeGroup.Album)) }
    }

    @Test
    fun getArtistsAlbums_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> ArtistsApis(client).getArtistsAlbums("id", includeGroups = listOf(IncludeGroup.Album)) }
    }

    @Test
    fun getArtistsAlbums_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> ArtistsApis(client).getArtistsAlbums("id", includeGroups = listOf(IncludeGroup.Album)) }
    }
    @Test
    fun getArtistsTopTracks_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> ArtistsApis(client).getArtistsTopTracks("id") }
    }

    @Test
    fun getArtistsTopTracks_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> ArtistsApis(client).getArtistsTopTracks("id") }
    }

    @Test
    fun getArtistsTopTracks_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> ArtistsApis(client).getArtistsTopTracks("id") }
    }

    @Test
    fun getArtistsTopTracks_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> ArtistsApis(client).getArtistsTopTracks("id") }
    }

    @Suppress("DEPRECATION")
    @Test
    fun getArtistsRelatedArtists_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> ArtistsApis(client).getArtistsRelatedArtists("id") }
    }

    @Test
    fun getArtistsRelatedArtists_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> ArtistsApis(client).getArtistsRelatedArtists("id") }
    }

    @Test
    fun getArtistsRelatedArtists_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> ArtistsApis(client).getArtistsRelatedArtists("id") }
    }

    @Test
    fun getArtistsRelatedArtists_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> ArtistsApis(client).getArtistsRelatedArtists("id") }
    }
}
