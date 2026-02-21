@file:Suppress("DEPRECATION")

package com.nubasu.kotlin_spotify_web_api_wrapper.api

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyResponseData
import com.nubasu.kotlin_spotify_web_api_wrapper.api.playlists.PlaylistsApis
import com.nubasu.kotlin_spotify_web_api_wrapper.request.playlists.AddItemsToPlaylistRequest
import com.nubasu.kotlin_spotify_web_api_wrapper.request.playlists.ChangePlaylistDetailsRequest
import com.nubasu.kotlin_spotify_web_api_wrapper.request.playlists.CreatePlaylistRequest
import com.nubasu.kotlin_spotify_web_api_wrapper.request.playlists.RemovePlaylistItemsRequest
import com.nubasu.kotlin_spotify_web_api_wrapper.request.playlists.TrackUri
import com.nubasu.kotlin_spotify_web_api_wrapper.request.playlists.UpdatePlaylistItemsRequest
import io.ktor.http.HttpStatusCode
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
        assertEquals(true, (res.data as SpotifyResponseData.Success).value)
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
        ApiStatusCaseAsserts.assertStatus201Created { client -> PlaylistsApis(client).removePlaylistItems("playlist-id", RemovePlaylistItemsRequest(items = listOf(TrackUri("spotify:track:1")))) }
    }

    @Test
    fun removePlaylistItems_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> PlaylistsApis(client).removePlaylistItems("playlist-id", RemovePlaylistItemsRequest(items = listOf(TrackUri("spotify:track:1")))) }
    }

    @Test
    fun removePlaylistItems_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> PlaylistsApis(client).removePlaylistItems("playlist-id", RemovePlaylistItemsRequest(items = listOf(TrackUri("spotify:track:1")))) }
    }

    @Test
    fun removePlaylistItems_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> PlaylistsApis(client).removePlaylistItems("playlist-id", RemovePlaylistItemsRequest(items = listOf(TrackUri("spotify:track:1")))) }
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

    @Test
    fun getPlaylist_success_allowsFollowersAndItemsPayload() = runTest {
        val body = """
            {
              "collaborative": false,
              "description": "sample",
              "external_urls": {"spotify": "https://open.spotify.com/playlist/37i9dQZF1DXcBWIGoYBM5M"},
              "followers": {"href": null, "total": 123},
              "href": "https://api.spotify.com/v1/playlists/37i9dQZF1DXcBWIGoYBM5M",
              "id": "37i9dQZF1DXcBWIGoYBM5M",
              "images": null,
              "name": "Today's Top Hits",
              "owner": {
                "external_urls": {"spotify": "https://open.spotify.com/user/spotify"},
                "href": "https://api.spotify.com/v1/users/spotify",
                "id": "spotify",
                "type": "user",
                "uri": "spotify:user:spotify",
                "display_name": "Spotify"
              },
              "primary_color": null,
              "public": true,
              "snapshot_id": "snapshot-id",
              "items": {
                "href": "https://api.spotify.com/v1/playlists/37i9dQZF1DXcBWIGoYBM5M/items",
                "total": 1,
                "items": [
                  {
                    "added_at": "2026-02-21T00:00:00Z",
                    "added_by": {
                      "external_urls": {"spotify": "https://open.spotify.com/user/spotify"},
                      "href": "https://api.spotify.com/v1/users/spotify",
                      "id": "spotify",
                      "type": "user",
                      "uri": "spotify:user:spotify"
                    },
                    "is_local": false,
                    "item": {"type": "track", "id": "4uLU6hMCjMI75M1A2tKUQC"}
                  }
                ]
              },
              "type": "playlist",
              "uri": "spotify:playlist:37i9dQZF1DXcBWIGoYBM5M"
            }
        """.trimIndent()

        val api = PlaylistsApis(
            ApiTestClientFactory.successClient(
                status = HttpStatusCode.OK,
                body = body,
            )
        )
        val response = api.getPlaylist("playlist-id")
        val data = response.data as SpotifyResponseData.Success

        assertEquals(HttpStatusCode.OK.value, response.statusCode)
        assertEquals(123, data.value.followers?.total)
        assertEquals(1, data.value.items?.items?.size)
        assertEquals("track", data.value.items?.items?.first()?.item?.let { (it as? com.nubasu.kotlin_spotify_web_api_wrapper.response.tracks.TrackObject)?.type })
    }

    @Test
    fun getCurrentUsersPlaylists_success_allowsNullImages() = runTest {
        val body = """
            {
              "href":"https://api.spotify.com/v1/me/playlists?offset=0&limit=20",
              "limit":20,
              "next":null,
              "offset":0,
              "previous":null,
              "total":1,
              "items":[
                {
                  "collaborative":false,
                  "description":"sample",
                  "external_urls":{"spotify":"https://open.spotify.com/playlist/37i9dQZF1DXcBWIGoYBM5M"},
                  "href":"https://api.spotify.com/v1/playlists/37i9dQZF1DXcBWIGoYBM5M",
                  "id":"37i9dQZF1DXcBWIGoYBM5M",
                  "images":null,
                  "name":"Today's Top Hits",
                  "owner":{
                    "external_urls":{"spotify":"https://open.spotify.com/user/spotify"},
                    "href":"https://api.spotify.com/v1/users/spotify",
                    "id":"spotify",
                    "type":"user",
                    "uri":"spotify:user:spotify",
                    "display_name":"Spotify"
                  },
                  "primary_color":null,
                  "public":true,
                  "snapshot_id":"snapshot-id",
                  "tracks":{"href":"https://api.spotify.com/v1/playlists/37i9dQZF1DXcBWIGoYBM5M/tracks","total":10},
                  "items":{"href":"https://api.spotify.com/v1/playlists/37i9dQZF1DXcBWIGoYBM5M/tracks","total":10},
                  "type":"playlist",
                  "uri":"spotify:playlist:37i9dQZF1DXcBWIGoYBM5M"
                }
              ]
            }
        """.trimIndent()

        val api = PlaylistsApis(
            ApiTestClientFactory.successClient(
                status = HttpStatusCode.OK,
                body = body,
            )
        )
        val response = api.getCurrentUsersPlaylists()
        val data = response.data as SpotifyResponseData.Success

        assertEquals(HttpStatusCode.OK.value, response.statusCode)
        assertEquals(1, data.value.items.size)
        assertEquals(null, data.value.items.first().images)
        assertEquals(null, data.value.items.first().primaryColor)
        assertEquals(10, data.value.items.first().items?.total)
    }

    @Test
    fun getCurrentUsersPlaylists_success_allowsItemsWithoutTracks() = runTest {
        val body = """
            {
              "href":"https://api.spotify.com/v1/me/playlists?offset=0&limit=20",
              "limit":20,
              "next":null,
              "offset":0,
              "previous":null,
              "total":1,
              "items":[
                {
                  "collaborative":false,
                  "description":"sample",
                  "external_urls":{"spotify":"https://open.spotify.com/playlist/37i9dQZF1DXcBWIGoYBM5M"},
                  "href":"https://api.spotify.com/v1/playlists/37i9dQZF1DXcBWIGoYBM5M",
                  "id":"37i9dQZF1DXcBWIGoYBM5M",
                  "images":null,
                  "name":"Today's Top Hits",
                  "owner":{
                    "external_urls":{"spotify":"https://open.spotify.com/user/spotify"},
                    "href":"https://api.spotify.com/v1/users/spotify",
                    "id":"spotify",
                    "type":"user",
                    "uri":"spotify:user:spotify",
                    "display_name":"Spotify"
                  },
                  "primary_color":null,
                  "public":true,
                  "snapshot_id":"snapshot-id",
                  "items":{"href":"https://api.spotify.com/v1/playlists/37i9dQZF1DXcBWIGoYBM5M/tracks","total":11},
                  "type":"playlist",
                  "uri":"spotify:playlist:37i9dQZF1DXcBWIGoYBM5M"
                }
              ]
            }
        """.trimIndent()

        val api = PlaylistsApis(
            ApiTestClientFactory.successClient(
                status = HttpStatusCode.OK,
                body = body,
            )
        )
        val response = api.getCurrentUsersPlaylists()
        val data = response.data as SpotifyResponseData.Success

        assertEquals(HttpStatusCode.OK.value, response.statusCode)
        assertEquals(1, data.value.items.size)
        assertEquals(11, data.value.items.first().items?.total)
        assertEquals(0, data.value.items.first().tracks.total)
    }

    @Test
    fun removePlaylistItems_requestSerializesAsItems() = runTest {
        val body = """
            {"snapshot_id":"next"}
        """.trimIndent()
        val api = PlaylistsApis(
            ApiTestClientFactory.successClient(
                status = HttpStatusCode.OK,
                body = body,
            )
        )
        val response = api.removePlaylistItems(
            "playlist-id",
            RemovePlaylistItemsRequest(items = listOf(TrackUri("spotify:track:1"))),
        )
        val data = response.data as SpotifyResponseData.Success
        assertEquals(HttpStatusCode.OK.value, response.statusCode)
        assertEquals("next", data.value.snapshotId)
    }
}
