@file:Suppress("DEPRECATION")

package com.nubasu.kotlin_spotify_web_api_wrapper.api

import com.nubasu.kotlin_spotify_web_api_wrapper.api.playlists.PlaylistsApis
import com.nubasu.kotlin_spotify_web_api_wrapper.request.playlists.AddItemsToPlaylistRequest
import com.nubasu.kotlin_spotify_web_api_wrapper.request.playlists.ChangePlaylistDetailsRequest
import com.nubasu.kotlin_spotify_web_api_wrapper.request.playlists.CreatePlaylistRequest
import com.nubasu.kotlin_spotify_web_api_wrapper.request.playlists.RemovePlaylistItemsRequest
import com.nubasu.kotlin_spotify_web_api_wrapper.request.playlists.TrackUri
import com.nubasu.kotlin_spotify_web_api_wrapper.request.playlists.UpdatePlaylistItemsRequest
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PlaylistsApisTest {
    @Test
    fun nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlaylistsApis(client).getPlaylist("playlist-id") }
    }

    @Test
    fun nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlaylistsApis(client).getPlaylist("playlist-id") }
    }

    @Test
    fun nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlaylistsApis(client).getPlaylist("playlist-id") }
    }

    @Test
    fun nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlaylistsApis(client).getPlaylist("playlist-id") }
    }

    @Test
    fun created201_returnsStatusAndBody() = runTest {
        val api = PlaylistsApis(ApiTestClientFactory.successClient())
        val res = api.changePlaylistDetails("playlist-id", ChangePlaylistDetailsRequest(name = "n"))
        assertEquals(201, res.statusCode)
        assertEquals(true, res.data)
    }

    @Suppress("DEPRECATION")
    @Test
    fun getPlaylist_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlaylistsApis(client).getPlaylist("playlist-id") }
    }

    @Test
    fun getPlaylist_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlaylistsApis(client).getPlaylist("playlist-id") }
    }

    @Test
    fun getPlaylist_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlaylistsApis(client).getPlaylist("playlist-id") }
    }

    @Test
    fun getPlaylist_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlaylistsApis(client).getPlaylist("playlist-id") }
    }
    @Test
    fun changePlaylistDetails_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlaylistsApis(client).changePlaylistDetails("playlist-id", ChangePlaylistDetailsRequest(name = "n")) }
    }

    @Test
    fun changePlaylistDetails_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlaylistsApis(client).changePlaylistDetails("playlist-id", ChangePlaylistDetailsRequest(name = "n")) }
    }

    @Test
    fun changePlaylistDetails_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlaylistsApis(client).changePlaylistDetails("playlist-id", ChangePlaylistDetailsRequest(name = "n")) }
    }

    @Test
    fun changePlaylistDetails_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlaylistsApis(client).changePlaylistDetails("playlist-id", ChangePlaylistDetailsRequest(name = "n")) }
    }
    @Test
    fun getPlaylistItems_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlaylistsApis(client).getPlaylistItems("playlist-id") }
    }

    @Test
    fun getPlaylistItems_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlaylistsApis(client).getPlaylistItems("playlist-id") }
    }

    @Test
    fun getPlaylistItems_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlaylistsApis(client).getPlaylistItems("playlist-id") }
    }

    @Test
    fun getPlaylistItems_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlaylistsApis(client).getPlaylistItems("playlist-id") }
    }
    @Test
    fun updatePlaylistItems_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlaylistsApis(client).updatePlaylistItems("playlist-id", UpdatePlaylistItemsRequest(uris = listOf("spotify:track:1"))) }
    }

    @Test
    fun updatePlaylistItems_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlaylistsApis(client).updatePlaylistItems("playlist-id", UpdatePlaylistItemsRequest(uris = listOf("spotify:track:1"))) }
    }

    @Test
    fun updatePlaylistItems_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlaylistsApis(client).updatePlaylistItems("playlist-id", UpdatePlaylistItemsRequest(uris = listOf("spotify:track:1"))) }
    }

    @Test
    fun updatePlaylistItems_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlaylistsApis(client).updatePlaylistItems("playlist-id", UpdatePlaylistItemsRequest(uris = listOf("spotify:track:1"))) }
    }
    @Test
    fun addItemsToPlaylist_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlaylistsApis(client).addItemsToPlaylist("playlist-id", AddItemsToPlaylistRequest(uris = listOf("spotify:track:1"))) }
    }

    @Test
    fun addItemsToPlaylist_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlaylistsApis(client).addItemsToPlaylist("playlist-id", AddItemsToPlaylistRequest(uris = listOf("spotify:track:1"))) }
    }

    @Test
    fun addItemsToPlaylist_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlaylistsApis(client).addItemsToPlaylist("playlist-id", AddItemsToPlaylistRequest(uris = listOf("spotify:track:1"))) }
    }

    @Test
    fun addItemsToPlaylist_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlaylistsApis(client).addItemsToPlaylist("playlist-id", AddItemsToPlaylistRequest(uris = listOf("spotify:track:1"))) }
    }
    @Test
    fun removePlaylistItems_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlaylistsApis(client).removePlaylistItems("playlist-id", RemovePlaylistItemsRequest(tracks = listOf(TrackUri("spotify:track:1")))) }
    }

    @Test
    fun removePlaylistItems_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlaylistsApis(client).removePlaylistItems("playlist-id", RemovePlaylistItemsRequest(tracks = listOf(TrackUri("spotify:track:1")))) }
    }

    @Test
    fun removePlaylistItems_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlaylistsApis(client).removePlaylistItems("playlist-id", RemovePlaylistItemsRequest(tracks = listOf(TrackUri("spotify:track:1")))) }
    }

    @Test
    fun removePlaylistItems_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlaylistsApis(client).removePlaylistItems("playlist-id", RemovePlaylistItemsRequest(tracks = listOf(TrackUri("spotify:track:1")))) }
    }
    @Test
    fun getCurrentUsersPlaylists_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlaylistsApis(client).getCurrentUsersPlaylists() }
    }

    @Test
    fun getCurrentUsersPlaylists_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlaylistsApis(client).getCurrentUsersPlaylists() }
    }

    @Test
    fun getCurrentUsersPlaylists_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlaylistsApis(client).getCurrentUsersPlaylists() }
    }

    @Test
    fun getCurrentUsersPlaylists_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlaylistsApis(client).getCurrentUsersPlaylists() }
    }
    @Test
    fun getUsersPlaylists_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlaylistsApis(client).getUsersPlaylists("user-id") }
    }

    @Test
    fun getUsersPlaylists_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlaylistsApis(client).getUsersPlaylists("user-id") }
    }

    @Test
    fun getUsersPlaylists_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlaylistsApis(client).getUsersPlaylists("user-id") }
    }

    @Test
    fun getUsersPlaylists_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlaylistsApis(client).getUsersPlaylists("user-id") }
    }
    @Test
    fun createPlaylist_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlaylistsApis(client).createPlaylist(CreatePlaylistRequest(name = "test")) }
    }

    @Test
    fun createPlaylist_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlaylistsApis(client).createPlaylist(CreatePlaylistRequest(name = "test")) }
    }

    @Test
    fun createPlaylist_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlaylistsApis(client).createPlaylist(CreatePlaylistRequest(name = "test")) }
    }

    @Test
    fun createPlaylist_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlaylistsApis(client).createPlaylist(CreatePlaylistRequest(name = "test")) }
    }

    @Suppress("DEPRECATION")
    @Test
    fun createPlaylistDeprecated_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlaylistsApis(client).createPlaylist("user-id", CreatePlaylistRequest(name = "test")) }
    }

    @Test
    fun createPlaylistDeprecated_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlaylistsApis(client).createPlaylist("user-id", CreatePlaylistRequest(name = "test")) }
    }

    @Test
    fun createPlaylistDeprecated_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlaylistsApis(client).createPlaylist("user-id", CreatePlaylistRequest(name = "test")) }
    }

    @Test
    fun createPlaylistDeprecated_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlaylistsApis(client).createPlaylist("user-id", CreatePlaylistRequest(name = "test")) }
    }
    @Test
    fun getFeaturedPlaylists_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlaylistsApis(client).getFeaturedPlaylists() }
    }

    @Test
    fun getFeaturedPlaylists_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlaylistsApis(client).getFeaturedPlaylists() }
    }

    @Test
    fun getFeaturedPlaylists_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlaylistsApis(client).getFeaturedPlaylists() }
    }

    @Test
    fun getFeaturedPlaylists_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlaylistsApis(client).getFeaturedPlaylists() }
    }
    @Test
    fun getCategorysPlaylists_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlaylistsApis(client).getCategorysPlaylists("party") }
    }

    @Test
    fun getCategorysPlaylists_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlaylistsApis(client).getCategorysPlaylists("party") }
    }

    @Test
    fun getCategorysPlaylists_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlaylistsApis(client).getCategorysPlaylists("party") }
    }

    @Test
    fun getCategorysPlaylists_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlaylistsApis(client).getCategorysPlaylists("party") }
    }
    @Test
    fun getPlaylistCoverImage_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlaylistsApis(client).getPlaylistCoverImage("playlist-id") }
    }

    @Test
    fun getPlaylistCoverImage_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlaylistsApis(client).getPlaylistCoverImage("playlist-id") }
    }

    @Test
    fun getPlaylistCoverImage_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlaylistsApis(client).getPlaylistCoverImage("playlist-id") }
    }

    @Test
    fun getPlaylistCoverImage_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlaylistsApis(client).getPlaylistCoverImage("playlist-id") }
    }
    @Test
    fun addCustomPlaylistCoverImage_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlaylistsApis(client).addCustomPlaylistCoverImage("playlist-id", "base64jpeg") }
    }

    @Test
    fun addCustomPlaylistCoverImage_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlaylistsApis(client).addCustomPlaylistCoverImage("playlist-id", "base64jpeg") }
    }

    @Test
    fun addCustomPlaylistCoverImage_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlaylistsApis(client).addCustomPlaylistCoverImage("playlist-id", "base64jpeg") }
    }

    @Test
    fun addCustomPlaylistCoverImage_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlaylistsApis(client).addCustomPlaylistCoverImage("playlist-id", "base64jpeg") }
    }
}
