package com.nubasu.spotify.webapi.wrapper

interface Platform {
    val name: String
}

/**
 * Retrieves data for getPlatform.
 *
 * @return The resulting Platform value.
 */
expect fun getPlatform(): Platform
