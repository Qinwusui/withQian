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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import xyz.liusui.anki.data.MsgData
import xyz.liusui.anki.utils.logE
import java.text.SimpleDateFormat
import java.util.*

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
            receiveMsg().collect{
                it.logE()
            }
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

    suspend fun <T> sendMsg(msg: MsgData) {

    }

    suspend fun receiveMsg() = channelFlow {
        if (!this@Repo::wsSession.isInitialized) {
            return@channelFlow
        }
        wsSession.incoming.consumeEach {
            val msg = Json.decodeFromString<MsgData>(it.readBytes().decodeToString())
            msg.logE()
            MsgRepo.insertMsgToDb(msg)
            send(msg)
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
