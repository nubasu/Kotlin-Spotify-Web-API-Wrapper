package com.nubasu.spotify.webapi.wrapper

import com.nubasu.spotify.webapi.wrapper.utils.TokenHolder
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class TokenProviderForDebugTest {
    @AfterTest
    fun cleanup() {
        TokenHolder.token = ""
        TokenHolder.tokenProvider = null
    }

    @Test
    fun installDebugTokenFromLocalProperties_setsProvider() {
        installDebugTokenFromLocalProperties()
        assertNotNull(TokenHolder.tokenProvider)
    }
}
