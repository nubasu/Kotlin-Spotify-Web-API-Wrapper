package com.nubasu.kotlin_spotify_web_api_wrapper.api

import com.nubasu.kotlin_spotify_web_api_wrapper.api.fixtures.SpotifyApiFixtures
import com.nubasu.kotlin_spotify_web_api_wrapper.api.library.LibraryApis
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LibraryApisTest {
    @Test
    fun getUsersSavedLibrary_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created(
            successBody = SpotifyApiFixtures.LIBRARY_MIN,
            assertData = {
                assertEquals(1, it.items.size)
                assertEquals("2024-01-01T00:00:00Z", it.items.first().addedAt)
            }
        ) { client ->
            LibraryApis(client).getUsersSavedLibrary()
        }
    }

    @Test
    fun getUsersSavedLibrary_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client ->
            LibraryApis(client).getUsersSavedLibrary()
        }
    }

    @Test
    fun getUsersSavedLibrary_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client ->
            LibraryApis(client).getUsersSavedLibrary()
        }
    }

    @Test
    fun getUsersSavedLibrary_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client ->
            LibraryApis(client).getUsersSavedLibrary()
        }
    }

    @Test
    fun saveItemsForCurrentUser_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created<Boolean> { client ->
            LibraryApis(client).saveItemsForCurrentUser(listOf("spotify:track:t1"))
        }
    }

    @Test
    fun saveItemsForCurrentUser_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client ->
            LibraryApis(client).saveItemsForCurrentUser(listOf("spotify:track:t1"))
        }
    }

    @Test
    fun saveItemsForCurrentUser_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client ->
            LibraryApis(client).saveItemsForCurrentUser(listOf("spotify:track:t1"))
        }
    }

    @Test
    fun saveItemsForCurrentUser_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client ->
            LibraryApis(client).saveItemsForCurrentUser(listOf("spotify:track:t1"))
        }
    }

    @Test
    fun removeUsersSavedItems_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created<Boolean> { client ->
            LibraryApis(client).removeUsersSavedItems(listOf("spotify:track:t1"))
        }
    }

    @Test
    fun removeUsersSavedItems_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client ->
            LibraryApis(client).removeUsersSavedItems(listOf("spotify:track:t1"))
        }
    }

    @Test
    fun removeUsersSavedItems_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client ->
            LibraryApis(client).removeUsersSavedItems(listOf("spotify:track:t1"))
        }
    }

    @Test
    fun removeUsersSavedItems_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client ->
            LibraryApis(client).removeUsersSavedItems(listOf("spotify:track:t1"))
        }
    }

    @Test
    fun checkUsersSavedItems_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created(
            successBody = "[true,false]",
            assertData = {
                assertEquals(listOf(true, false), it)
            }
        ) { client ->
            LibraryApis(client).checkUsersSavedItems(listOf("spotify:track:t1", "spotify:track:t2"))
        }
    }

    @Test
    fun checkUsersSavedItems_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client ->
            LibraryApis(client).checkUsersSavedItems(listOf("spotify:track:t1"))
        }
    }

    @Test
    fun checkUsersSavedItems_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client ->
            LibraryApis(client).checkUsersSavedItems(listOf("spotify:track:t1"))
        }
    }

    @Test
    fun checkUsersSavedItems_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client ->
            LibraryApis(client).checkUsersSavedItems(listOf("spotify:track:t1"))
        }
    }
}
