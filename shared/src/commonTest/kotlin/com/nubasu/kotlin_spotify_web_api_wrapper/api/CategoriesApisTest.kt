package com.nubasu.spotify.webapi.wrapper.api

import com.nubasu.spotify.webapi.wrapper.response.common.SpotifyResponseData
import com.nubasu.spotify.webapi.wrapper.api.categories.CategoriesApis
import com.nubasu.spotify.webapi.wrapper.api.fixtures.SpotifyApiFixtures
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CategoriesApisTest {
    @Test
    fun nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> CategoriesApis(client).getSeveralBrowseCategories() }
    }

    @Test
    fun nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> CategoriesApis(client).getSeveralBrowseCategories() }
    }

    @Test
    fun nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> CategoriesApis(client).getSeveralBrowseCategories() }
    }

    @Test
    fun nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> CategoriesApis(client).getSeveralBrowseCategories() }
    }

    @Test
    fun created201_returnsStatusAndBody() = runTest {
        val api = CategoriesApis(ApiTestClientFactory.successClient(body = SpotifyApiFixtures.BROWSE_CATEGORIES_MIN))
        val res = api.getSeveralBrowseCategories()
        assertEquals(201, res.statusCode)
        val data = (res.data as SpotifyResponseData.Success).value
        assertEquals(1, data.categories.items.size)
    }
    @Test
    fun getSeveralBrowseCategories_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> CategoriesApis(client).getSeveralBrowseCategories() }
    }

    @Test
    fun getSeveralBrowseCategories_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> CategoriesApis(client).getSeveralBrowseCategories() }
    }

    @Test
    fun getSeveralBrowseCategories_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> CategoriesApis(client).getSeveralBrowseCategories() }
    }

    @Test
    fun getSeveralBrowseCategories_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> CategoriesApis(client).getSeveralBrowseCategories() }
    }
    @Test
    fun getSingleBrowseCategory_nonSuccess_throws_status201_created() = runTest {
        ApiStatusCaseAsserts.assertStatus201Created { client -> CategoriesApis(client).getSingleBrowseCategory("toplists") }
    }

    @Test
    fun getSingleBrowseCategory_nonSuccess_throws_status401_unauthorized() = runTest {
        ApiStatusCaseAsserts.assertStatus401Unauthorized { client -> CategoriesApis(client).getSingleBrowseCategory("toplists") }
    }

    @Test
    fun getSingleBrowseCategory_nonSuccess_throws_status403_forbidden() = runTest {
        ApiStatusCaseAsserts.assertStatus403Forbidden { client -> CategoriesApis(client).getSingleBrowseCategory("toplists") }
    }

    @Test
    fun getSingleBrowseCategory_nonSuccess_throws_status429_tooManyRequests() = runTest {
        ApiStatusCaseAsserts.assertStatus429TooManyRequests { client -> CategoriesApis(client).getSingleBrowseCategory("toplists") }
    }
}
