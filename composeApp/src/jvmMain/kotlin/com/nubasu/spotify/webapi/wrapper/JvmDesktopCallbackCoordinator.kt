package com.nubasu.spotify.webapi.wrapper

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class JvmDesktopCallbackCoordinator(
    private val host: String = "127.0.0.1",
    private val port: Int = 8888,
    private val path: String = "/callback",
) : DesktopCallbackCoordinator {
    private val callbackUris = ConcurrentLinkedQueue<String>()
    private var server: HttpServer? = null
    private var executor: ExecutorService? = null

    override fun beginSession(): String? {
        if (server == null) {
            val created = runCatching {
                HttpServer.create(InetSocketAddress(host, port), 0)
            }.getOrNull() ?: return null

            created.createContext(path) { exchange ->
                handleCallback(exchange)
            }
            val serverExecutor = Executors.newSingleThreadExecutor()
            created.executor = serverExecutor
            created.start()
            server = created
            executor = serverExecutor
        }
        return "http://$host:$port$path"
    }

    override fun consumeCallbackUri(): String? {
        return callbackUris.poll()
    }

    override fun close() {
        server?.stop(0)
        server = null
        executor?.shutdownNow()
        executor = null
    }

    private fun handleCallback(exchange: HttpExchange) {
        val request = exchange.requestURI
        val query = request.rawQuery?.let { "?$it" }.orEmpty()
        val callbackUri = "http://$host:$port${request.rawPath}$query"
        callbackUris.offer(callbackUri)

        val body = """
            <html>
              <body>
                <h3>Spotify authorization completed.</h3>
                <p>You can close this tab and return to the app.</p>
              </body>
            </html>
        """.trimIndent().encodeToByteArray()

        exchange.responseHeaders.add("Content-Type", "text/html; charset=utf-8")
        exchange.sendResponseHeaders(200, body.size.toLong())
        exchange.responseBody.use { out -> out.write(body) }
    }
}
