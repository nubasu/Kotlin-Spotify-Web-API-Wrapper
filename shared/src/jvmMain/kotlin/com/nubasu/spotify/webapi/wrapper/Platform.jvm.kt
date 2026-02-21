package com.nubasu.spotify.webapi.wrapper

class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

/**
 * Retrieves data for getPlatform.
 *
 * @return The resulting Platform value.
 */
actual fun getPlatform(): Platform = JVMPlatform()
