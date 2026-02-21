package com.nubasu.spotify.webapi.wrapper.response.player

import com.nubasu.spotify.webapi.wrapper.response.episodes.Episode
import com.nubasu.spotify.webapi.wrapper.response.tracks.TrackObject
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object PlaybackItemSerializer :
    JsonContentPolymorphicSerializer<PlaybackItem>(PlaybackItem::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<PlaybackItem> {
        val obj = element.jsonObject
        val type = obj["type"]?.jsonPrimitive?.contentOrNull

        return when {
            type == "episode" || "show" in obj -> Episode.serializer()
            type == "track" || "album" in obj || "artists" in obj -> TrackObject.serializer()
            else -> UnknownPlaybackItem.serializer()
        }
    }
}

@Serializable(with = UnknownPlaybackItemSerializer::class)
data class UnknownPlaybackItem(
    val raw: JsonObject,
) : PlaybackItem

object UnknownPlaybackItemSerializer : KSerializer<UnknownPlaybackItem> {
    override val descriptor =
        buildClassSerialDescriptor("UnknownPlaybackItem") {
            element("raw", JsonObject.serializer().descriptor)
        }

    override fun deserialize(decoder: Decoder): UnknownPlaybackItem {
        val jsonDecoder =
            decoder as? JsonDecoder
                ?: throw SerializationException("UnknownPlaybackItem is JSON-only")
        return UnknownPlaybackItem(jsonDecoder.decodeJsonElement().jsonObject)
    }

    override fun serialize(
        encoder: Encoder,
        value: UnknownPlaybackItem,
    ) {
        val jsonEncoder =
            encoder as? JsonEncoder
                ?: throw SerializationException("UnknownPlaybackItem is JSON-only")
        jsonEncoder.encodeJsonElement(value.raw)
    }
}
