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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import xyz.liusui.anki.data.MsgData
import xyz.liusui.anki.utils.logD
import xyz.liusui.anki.utils.logE
import java.text.SimpleDateFormat

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
    val msg = MutableStateFlow(
        MsgData(
            0,
            "",
            0,
            "",
            0,
            "",
            0,
            "",
            "",
            ""
        )
    )
    var msgList = MutableStateFlow(mutableListOf<MsgData>())

    init {
        suspendScope.launch {
            MsgRepo.queryAll().collect {
                msgList.value = it.distinctBy {msgData->
                    msgData.senderId
                }.sortedByDescending {msgData->
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").parse(msgData.t).time
                }.toMutableList()
            }
        }
        suspendScope.launch {
            initWsSession()
            receiveMsg().collect { msgData ->
                msg.value = msgData
                MsgRepo.insertMsgToDb(msgData)
                msgList.value = msgList.value.dropWhile {
                    it.senderId == msgData.senderId
                }.toMutableList().apply {
                    add(msgData)
                }.sortedByDescending {
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").parse(it.t).time
                }.toMutableList()
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
                e.logE()
            }
        }
    }

    suspend fun <T> sendMsg(msg: MsgData) {

    }

    suspend fun receiveMsg() = channelFlow {
        "www".logD()
        wsSession.incoming.consumeEach { f ->
            val json = Json {
                ignoreUnknownKeys = true
            }
            val msgData = json.decodeFromString<MsgData>(f.data.decodeToString())

            send(msgData)
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
