package xyz.liusui.anki.repo

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import xyz.liusui.anki.data.Msg
import xyz.liusui.anki.data.TextMsg

object Repo {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
        }
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens("token", "Qinsansui233...")
                }
            }
        }
        install(WebSockets)
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
                    bearerAuth("Qinsansui233...")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun <T> sendMsg(msg: Msg<T>) {

    }

    suspend fun receiveTextMsg() = channelFlow<Msg<TextMsg>> {
        wsSession.incoming.receiveAsFlow().collect {

        }
    }

    private fun <T> flowByIO(block: suspend () -> T) = flow {
        try {
            emit(Result.success(block()))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}
