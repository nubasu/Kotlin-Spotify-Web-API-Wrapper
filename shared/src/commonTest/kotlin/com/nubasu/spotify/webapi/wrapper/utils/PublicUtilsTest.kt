package com.nubasu.spotify.webapi.wrapper.utils

import com.nubasu.spotify.webapi.wrapper.getPlatform
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PublicUtilsTest {
    @AfterTest
    fun cleanup() {
        TokenHolder.token = ""
        TokenHolder.tokenProvider = null
    }

    @Test
    fun tokenHolder_getTokenFromProvider_works() {
        TokenHolder.tokenProvider = { "token-from-provider" }
        val token = TokenHolder.getTokenFromProvider()
        assertEquals("token-from-provider", token)
    }

    @Test
    fun tokenHolder_getTokenFromProvider_throwsWhenMissing() {
        assertFailsWith<IllegalStateException> {
            TokenHolder.getTokenFromProvider()
        }
    }

    @Test
    fun countryCode_helpers_work() {
        val locale = CountryCode.US.toLocale("en")
        val fromCode = CountryCode.fromCode("US")
        assertEquals("en_US", locale)
        assertEquals(CountryCode.US, fromCode)
    }

    @Test
    fun getPlatform_returnsPlatform() {
        val platform = getPlatform()
        assertNotNull(platform)
        assertTrue(platform.name.isNotBlank())
    }
}
