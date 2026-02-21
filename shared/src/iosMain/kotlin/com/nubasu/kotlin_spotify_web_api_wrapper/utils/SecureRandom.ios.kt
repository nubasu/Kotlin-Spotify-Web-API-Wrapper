package com.nubasu.kotlin_spotify_web_api_wrapper.utils

import kotlinx.cinterop.addressOf
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.usePinned
import platform.posix.arc4random_buf

@OptIn(ExperimentalForeignApi::class)
internal actual fun secureRandomBytes(length: Int): ByteArray {
    require(length > 0) { "length must be > 0" }
    val out = ByteArray(length)
    out.usePinned { pinned ->
        arc4random_buf(pinned.addressOf(0), length.toULong())
    }
    return out
}
