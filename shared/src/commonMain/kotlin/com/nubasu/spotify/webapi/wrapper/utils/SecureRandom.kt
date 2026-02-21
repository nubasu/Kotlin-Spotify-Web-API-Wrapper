package com.nubasu.spotify.webapi.wrapper.utils

/**
 * Executes secureRandomBytes.
 *
 * @param length The length parameter.
 * @return The resulting ByteArray value.
 */
internal expect fun secureRandomBytes(length: Int): ByteArray
