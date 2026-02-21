package com.nubasu.spotify.webapi.wrapper.utils

import java.security.SecureRandom

private val secureRandom = SecureRandom()

/**
 * Executes secureRandomBytes.
 *
 * @param length The length parameter.
 * @return The resulting ByteArray value.
 */
internal actual fun secureRandomBytes(length: Int): ByteArray {
    require(length > 0) { "length must be > 0" }
    val out = ByteArray(length)
    secureRandom.nextBytes(out)
    return out
}
