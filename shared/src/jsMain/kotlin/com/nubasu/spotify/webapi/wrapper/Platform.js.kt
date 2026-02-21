package com.nubasu.spotify.webapi.wrapper

class JsPlatform : Platform {
    override val name: String = "Web with Kotlin/JS"
}

/**
 * Retrieves data for getPlatform.
 *
 * @return The resulting Platform value.
 */
actual fun getPlatform(): Platform = JsPlatform()
