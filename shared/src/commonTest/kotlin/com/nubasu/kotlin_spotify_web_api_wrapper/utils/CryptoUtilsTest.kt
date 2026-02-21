package com.nubasu.kotlin_spotify_web_api_wrapper.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CryptoUtilsTest {
    @Test
    fun sha256_digest_matchesKnownVector() {
        val digest = Sha256.digest("abc".encodeToByteArray())
        assertEquals(
            "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad",
            digest.toHex(),
        )
    }

    @Test
    fun secureRandomBytes_returnsRequestedLength() {
        val bytes = secureRandomBytes(32)
        assertEquals(32, bytes.size)
    }

    @Test
    fun secureRandomBytes_throwsWhenLengthIsZero() {
        assertFailsWith<IllegalArgumentException> {
            secureRandomBytes(0)
        }
    }

    private fun ByteArray.toHex(): String {
        val digits = "0123456789abcdef"
        val chars = CharArray(size * 2)
        for (i in indices) {
            val value = this[i].toInt() and 0xff
            chars[i * 2] = digits[value ushr 4]
            chars[i * 2 + 1] = digits[value and 0x0f]
        }
        return chars.concatToString()
    }
}
