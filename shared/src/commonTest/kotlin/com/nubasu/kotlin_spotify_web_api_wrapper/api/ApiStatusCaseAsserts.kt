package com.nubasu.kotlin_spotify_web_api_wrapper.api

import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyApiResponse
import com.nubasu.kotlin_spotify_web_api_wrapper.response.common.SpotifyResponseData
import io.ktor.client.HttpClient
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.serializer
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.fail

internal object ApiStatusCaseAsserts {
    private val json = Json { ignoreUnknownKeys = true }

    suspend inline fun <reified T> assertStatus201Created(
        successBody: String? = null,
        crossinline assertData: (T) -> Unit = {},
        invoke: suspend (HttpClient) -> SpotifyApiResponse<T>,
    ) {
        val body = successBody ?: defaultSuccessBody<T>()
        val response = invoke(ApiTestClientFactory.successClient(HttpStatusCode.Created, body))
        assertEquals(201, response.statusCode)

        val data = response.data
        if (data !is SpotifyResponseData.Success) {
            fail("201 must return success data, but was: $data")
        }
        val actual = data.value

        if (actual is Boolean) {
            assertEquals(true, actual)
        } else {
            val expected = runCatching { json.decodeFromString<T>(body) }
                .getOrElse { error ->
                    fail("Cannot decode expected success body for 201 assertion: ${error.message}")
                }
            assertEquals(expected, actual)
            assertNotNull(actual)
        }

        assertData(actual)
    }

    @PublishedApi
    internal inline fun <reified T> defaultSuccessBody(): String {
        if (T::class == Boolean::class) {
            return "{}"
        }
        val descriptor = serializer<T>().descriptor
        return defaultJsonElement(descriptor, 0).toString()
    }

    @PublishedApi
    internal fun defaultJsonElement(
        descriptor: SerialDescriptor,
        depth: Int,
    ): JsonElement {
        if (depth > 8) return JsonNull
        val serialName = descriptor.serialName
        if (serialName.startsWith("kotlin.collections.List") || serialName.startsWith("kotlin.collections.Set")) {
            return JsonArray(emptyList())
        }
        if (serialName.startsWith("kotlin.collections.Map")) {
            return JsonObject(emptyMap())
        }

        val kind = descriptor.kind
        if (kind is PrimitiveKind) {
            return when (kind) {
                PrimitiveKind.BOOLEAN -> JsonPrimitive(false)
                PrimitiveKind.BYTE,
                PrimitiveKind.SHORT,
                PrimitiveKind.INT,
                PrimitiveKind.LONG -> JsonPrimitive(0)
                PrimitiveKind.FLOAT,
                PrimitiveKind.DOUBLE -> JsonPrimitive(0.0)
                PrimitiveKind.CHAR,
                PrimitiveKind.STRING -> JsonPrimitive("x")
            }
        }

        return when (kind) {
            SerialKind.ENUM -> {
                if (descriptor.elementsCount > 0) {
                    JsonPrimitive(descriptor.getElementName(0))
                } else {
                    JsonPrimitive("x")
                }
            }
            StructureKind.LIST -> JsonArray(emptyList())
            StructureKind.MAP -> JsonObject(emptyMap())
            StructureKind.CLASS,
            StructureKind.OBJECT -> {
                val content = buildMap {
                    for (i in 0 until descriptor.elementsCount) {
                        val child = descriptor.getElementDescriptor(i)
                        val value = if (child.isNullable) JsonNull else defaultJsonElement(child, depth + 1)
                        put(descriptor.getElementName(i), value)
                    }
                }
                JsonObject(content)
            }
            else -> JsonObject(emptyMap())
        }
    }

    suspend fun <T> assertStatus401Unauthorized(
        invoke: suspend (HttpClient) -> SpotifyApiResponse<T>,
    ) {
        val response = invoke(ApiTestClientFactory.errorClient(HttpStatusCode.Unauthorized))
        assertEquals(401, response.statusCode)
        val error = response.data as SpotifyResponseData.Error
        assertEquals(401, error.value.error.status)
        assertEquals("test-error", error.value.error.message)
    }

    suspend fun <T> assertStatus403Forbidden(
        invoke: suspend (HttpClient) -> SpotifyApiResponse<T>,
    ) {
        val response = invoke(ApiTestClientFactory.errorClient(HttpStatusCode.Forbidden))
        assertEquals(403, response.statusCode)
        val error = response.data as SpotifyResponseData.Error
        assertEquals(403, error.value.error.status)
        assertEquals("test-error", error.value.error.message)
    }

    suspend fun <T> assertStatus429TooManyRequests(
        invoke: suspend (HttpClient) -> SpotifyApiResponse<T>,
    ) {
        val response = invoke(ApiTestClientFactory.errorClient(HttpStatusCode.TooManyRequests))
        assertEquals(429, response.statusCode)
        val error = response.data as SpotifyResponseData.Error
        assertEquals(429, error.value.error.status)
        assertEquals("test-error", error.value.error.message)
    }
}
