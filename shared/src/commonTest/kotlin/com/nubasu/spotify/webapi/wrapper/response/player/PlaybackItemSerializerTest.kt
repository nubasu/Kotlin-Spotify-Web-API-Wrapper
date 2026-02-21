package com.nubasu.spotify.webapi.wrapper.response.player

import com.nubasu.spotify.webapi.wrapper.response.episodes.Episode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import com.nubasu.spotify.webapi.wrapper.response.tracks.TrackObject as PlaybackTrack

class PlaybackItemSerializerTest {
    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun playbackItem_deserializesToEpisode_forEpisodeType() {
        val item =
            json.decodeFromString<PlaybackItem>(
                """
                {
                  "type": "episode",
                  "description": "d",
                  "html_description": "d",
                  "duration_ms": 1,
                  "explicit": false,
                  "external_urls": {"spotify":"s"},
                  "href": "h",
                  "id": "e1",
                  "images": [{"url":"u","height":1,"width":1}],
                  "is_externally_hosted": false,
                  "is_playable": true,
                  "languages": ["en"],
                  "name": "n",
                  "release_date": "2024-01-01",
                  "release_date_precision": "day",
                  "uri": "spotify:episode:e1",
                  "show": {
                    "available_markets": ["US"],
                    "copyrights": [],
                    "description": "sd",
                    "html_description": "sd",
                    "explicit": false,
                    "external_urls": {"spotify":"s"},
                    "href": "h",
                    "id": "s1",
                    "images": [{"url":"u","height":1,"width":1}],
                    "is_external_hosted": false,
                    "languages": ["en"],
                    "media_type": "audio",
                    "name": "show",
                    "publisher": "pub",
                    "type": "show",
                    "uri": "spotify:show:s1",
                    "total_episodes": 1
                  }
                }
                """.trimIndent(),
            )
        assertTrue(item is Episode)
    }

    @Test
    fun playbackItem_deserializesToTrack_forTrackType() {
        val item =
            json.decodeFromString<PlaybackItem>(
                """{"type":"track","id":"t1","name":"track"}""",
            )
        assertTrue(item is PlaybackTrack)
    }

    @Test
    fun playbackItem_deserializesToUnknown_forUnsupportedType() {
        val item =
            json.decodeFromString<PlaybackItem>(
                """{"type":"unknown","foo":"bar"}""",
            )
        assertTrue(item is UnknownPlaybackItem)
    }

    @Test
    fun unknownPlaybackItemSerializer_roundTripsRawJson() {
        val raw =
            buildJsonObject {
                put("foo", JsonPrimitive("bar"))
            }

        val encoded = json.encodeToString(UnknownPlaybackItem.serializer(), UnknownPlaybackItem(raw))
        val decoded = json.decodeFromJsonElement<UnknownPlaybackItem>(Json.parseToJsonElement(encoded))

        assertEquals("bar", decoded.raw["foo"]?.jsonPrimitive?.content)
        assertTrue(decoded.raw.containsKey("foo"))
    }
}
