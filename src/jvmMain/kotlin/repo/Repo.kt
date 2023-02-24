package repo

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json

object Repo {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
        }
    }

    private lateinit var wsSession: WebSocketSession
    private val suspendScope = CoroutineScope(Dispatchers.IO)

    init {
        suspendScope.launch {
            initWsSession()
        }
    }

    private suspend fun initWsSession() {
        if (!this::wsSession.isInitialized) {
            try {
                wsSession = client.webSocketSession {
                    url(urlString = "ws://127.0.0.1:54322/anki/chat")
                    method = HttpMethod.Get
                }
            } catch (e: Exception) {
                withTimeout(2000) {
                    println("重新尝试连接服务器...")
                    initWsSession()
                }
            }
        }
    }
}
