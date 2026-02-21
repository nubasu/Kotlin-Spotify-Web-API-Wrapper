package com.nubasu.kotlin_spotify_web_api_wrapper.utils

import java.security.SecureRandom

private val secureRandom = SecureRandom()

internal actual fun secureRandomBytes(length: Int): ByteArray {
    require(length > 0) { "length must be > 0" }
    val out = ByteArray(length)
    secureRandom.nextBytes(out)
    return out
}
