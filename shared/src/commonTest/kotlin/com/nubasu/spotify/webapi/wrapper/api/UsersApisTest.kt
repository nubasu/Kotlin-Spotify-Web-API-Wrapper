package com.nubasu.spotify.webapi.wrapper.api

import com.nubasu.spotify.webapi.wrapper.api.users.UsersApis
import com.nubasu.spotify.webapi.wrapper.request.users.FollowType
import com.nubasu.spotify.webapi.wrapper.request.users.TopItemType
import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyResponseData
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UsersApisTest {
    @Test
    fun nonSuccess_throws_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created { client -> UsersApis(client).getCurrentUsersProfile() }
        }

    @Test
    fun nonSuccess_throws_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> UsersApis(client).getCurrentUsersProfile() }
        }

    @Test
    fun nonSuccess_throws_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client -> UsersApis(client).getCurrentUsersProfile() }
        }

    @Test
    fun nonSuccess_throws_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> UsersApis(client).getCurrentUsersProfile() }
        }

    @Test
    fun created201_returnsStatusAndBody() =
        runTest {
            val api = UsersApis(ApiTestClientFactory.successClient())
            val res = api.followPlaylist("playlist-id")
            assertEquals(201, res.statusCode)
            assertEquals(true, (res.data as SpotifyResponseData.Success).value)
        }

    @Test
    fun getCurrentUsersProfile_nonSuccess_throws_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created { client -> UsersApis(client).getCurrentUsersProfile() }
        }

    @Test
    fun getCurrentUsersProfile_nonSuccess_throws_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> UsersApis(client).getCurrentUsersProfile() }
        }

    @Test
    fun getCurrentUsersProfile_nonSuccess_throws_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client -> UsersApis(client).getCurrentUsersProfile() }
        }

    @Test
    fun getCurrentUsersProfile_nonSuccess_throws_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> UsersApis(client).getCurrentUsersProfile() }
        }

    @Test
    fun getUsersTopItems_nonSuccess_throws_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created { client -> UsersApis(client).getUsersTopItems(TopItemType.ARTISTS) }
        }

    @Test
    fun getUsersTopItems_nonSuccess_throws_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> UsersApis(client).getUsersTopItems(TopItemType.ARTISTS) }
        }

    @Test
    fun getUsersTopItems_nonSuccess_throws_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client -> UsersApis(client).getUsersTopItems(TopItemType.ARTISTS) }
        }

    @Test
    fun getUsersTopItems_nonSuccess_throws_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> UsersApis(client).getUsersTopItems(TopItemType.ARTISTS) }
        }

    @Test
    fun getUsersProfile_nonSuccess_throws_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created { client -> UsersApis(client).getUsersProfile("user-id") }
        }

    @Test
    fun getUsersProfile_nonSuccess_throws_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> UsersApis(client).getUsersProfile("user-id") }
        }

    @Test
    fun getUsersProfile_nonSuccess_throws_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client -> UsersApis(client).getUsersProfile("user-id") }
        }

    @Test
    fun getUsersProfile_nonSuccess_throws_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> UsersApis(client).getUsersProfile("user-id") }
        }

    @Test
    fun followPlaylist_nonSuccess_throws_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created { client -> UsersApis(client).followPlaylist("playlist-id") }
        }

    @Test
    fun followPlaylist_nonSuccess_throws_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> UsersApis(client).followPlaylist("playlist-id") }
        }

    @Test
    fun followPlaylist_nonSuccess_throws_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client -> UsersApis(client).followPlaylist("playlist-id") }
        }

    @Test
    fun followPlaylist_nonSuccess_throws_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> UsersApis(client).followPlaylist("playlist-id") }
        }

    @Test
    fun unfollowPlaylist_nonSuccess_throws_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created { client -> UsersApis(client).unfollowPlaylist("playlist-id") }
        }

    @Test
    fun unfollowPlaylist_nonSuccess_throws_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> UsersApis(client).unfollowPlaylist("playlist-id") }
        }

    @Test
    fun unfollowPlaylist_nonSuccess_throws_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client -> UsersApis(client).unfollowPlaylist("playlist-id") }
        }

    @Test
    fun unfollowPlaylist_nonSuccess_throws_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> UsersApis(client).unfollowPlaylist("playlist-id") }
        }

    @Test
    fun getFollowedArtists_nonSuccess_throws_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created { client -> UsersApis(client).getFollowedArtists(FollowType.ARTIST) }
        }

    @Test
    fun getFollowedArtists_nonSuccess_throws_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> UsersApis(client).getFollowedArtists(FollowType.ARTIST) }
        }

    @Test
    fun getFollowedArtists_nonSuccess_throws_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client -> UsersApis(client).getFollowedArtists(FollowType.ARTIST) }
        }

    @Test
    fun getFollowedArtists_nonSuccess_throws_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> UsersApis(client).getFollowedArtists(FollowType.ARTIST) }
        }

    @Test
    fun followArtistsOrUsers_nonSuccess_throws_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created { client ->
                UsersApis(client).followArtistsOrUsers(FollowType.ARTIST, listOf("id1"))
            }
        }

    @Test
    fun followArtistsOrUsers_nonSuccess_throws_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client ->
                UsersApis(client).followArtistsOrUsers(FollowType.ARTIST, listOf("id1"))
            }
        }

    @Test
    fun followArtistsOrUsers_nonSuccess_throws_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client ->
                UsersApis(client).followArtistsOrUsers(FollowType.ARTIST, listOf("id1"))
            }
        }

    @Test
    fun followArtistsOrUsers_nonSuccess_throws_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client ->
                UsersApis(client).followArtistsOrUsers(FollowType.ARTIST, listOf("id1"))
            }
        }

    @Test
    fun unfollowArtistsOrUsers_nonSuccess_throws_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created { client ->
                UsersApis(client).unfollowArtistsOrUsers(FollowType.ARTIST, listOf("id1"))
            }
        }

    @Test
    fun unfollowArtistsOrUsers_nonSuccess_throws_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client ->
                UsersApis(client).unfollowArtistsOrUsers(FollowType.ARTIST, listOf("id1"))
            }
        }

    @Test
    fun unfollowArtistsOrUsers_nonSuccess_throws_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client ->
                UsersApis(client).unfollowArtistsOrUsers(FollowType.ARTIST, listOf("id1"))
            }
        }

    @Test
    fun unfollowArtistsOrUsers_nonSuccess_throws_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client ->
                UsersApis(client).unfollowArtistsOrUsers(FollowType.ARTIST, listOf("id1"))
            }
        }

    @Test
    fun checkIfUserFollowsArtistsOrUsers_nonSuccess_throws_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created { client ->
                UsersApis(client).checkIfUserFollowsArtistsOrUsers(FollowType.ARTIST, listOf("id1"))
            }
        }

    @Test
    fun checkIfUserFollowsArtistsOrUsers_nonSuccess_throws_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client ->
                UsersApis(client).checkIfUserFollowsArtistsOrUsers(FollowType.ARTIST, listOf("id1"))
            }
        }

    @Test
    fun checkIfUserFollowsArtistsOrUsers_nonSuccess_throws_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client ->
                UsersApis(client).checkIfUserFollowsArtistsOrUsers(FollowType.ARTIST, listOf("id1"))
            }
        }

    @Test
    fun checkIfUserFollowsArtistsOrUsers_nonSuccess_throws_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client ->
                UsersApis(client).checkIfUserFollowsArtistsOrUsers(FollowType.ARTIST, listOf("id1"))
            }
        }

    @Test
    fun checkIfCurrentUserFollowsPlaylist_nonSuccess_throws_status201_created() =
        runTest {
            ApiStatusCaseAsserts.assertStatus201Created { client ->
                UsersApis(client).checkIfCurrentUserFollowsPlaylist("playlist-id", listOf("user-id"))
            }
        }

    @Test
    fun checkIfCurrentUserFollowsPlaylist_nonSuccess_throws_status401_unauthorized() =
        runTest {
            ApiStatusCaseAsserts.assertStatus401Unauthorized { client ->
                UsersApis(client).checkIfCurrentUserFollowsPlaylist("playlist-id", listOf("user-id"))
            }
        }

    @Test
    fun checkIfCurrentUserFollowsPlaylist_nonSuccess_throws_status403_forbidden() =
        runTest {
            ApiStatusCaseAsserts.assertStatus403Forbidden { client ->
                UsersApis(client).checkIfCurrentUserFollowsPlaylist("playlist-id", listOf("user-id"))
            }
        }

    @Test
    fun checkIfCurrentUserFollowsPlaylist_nonSuccess_throws_status429_tooManyRequests() =
        runTest {
            ApiStatusCaseAsserts.assertStatus429TooManyRequests { client ->
                UsersApis(client).checkIfCurrentUserFollowsPlaylist("playlist-id", listOf("user-id"))
            }
        }
}
