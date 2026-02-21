package com.nubasu.kotlin_spotify_web_api_wrapper.utils

internal actual fun secureRandomBytes(length: Int): ByteArray {
    require(length > 0) { "length must be > 0" }
    val crypto = js("globalThis.crypto")
    if (crypto == null) {
        error("Web Crypto API is not available on this platform.")
    }
    val bytes = js("new Uint8Array(length)")
    crypto.getRandomValues(bytes)

    val out = ByteArray(length)
    for (idx in 0 until length) {
        out[idx] = (bytes[idx] as Int).toByte()
    }
    return out
}
