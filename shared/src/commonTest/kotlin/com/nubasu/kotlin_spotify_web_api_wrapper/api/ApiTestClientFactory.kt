package com.nubasu.kotlin_spotify_web_api_wrapper.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json

internal object ApiTestClientFactory {
    fun errorClient(status: HttpStatusCode = HttpStatusCode.InternalServerError): HttpClient {
        val engine = MockEngine {
            respond(
                content = """{"error":"test-error"}""",
                status = status,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
        }
        return client(engine)
    }

    fun successClient(
        status: HttpStatusCode = HttpStatusCode.Created,
        body: String = "{}",
    ): HttpClient {
        val engine = MockEngine {
            respond(
                content = body,
                status = status,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
            )
        }
        return client(engine)
    }

    private fun client(engine: MockEngine): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json()
            }
        }
    }
}
