package com.nubasu.spotify.webapi.wrapper.api

import kotlinx.serialization.json.Json

/**
 * Shared [Json] instance used across all Spotify API serialization.
 *
 * - `ignoreUnknownKeys`: Spotify may add new fields at any time; ignore unknown JSON keys.
 * - `coerceInputValues`: Unknown enum values are coerced to `null` (for nullable fields)
 *   or the field default, preventing [kotlinx.serialization.SerializationException] on new API values.
 */
internal val SpotifyJson = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
}
