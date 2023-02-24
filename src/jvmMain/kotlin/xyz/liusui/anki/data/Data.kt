package xyz.liusui.anki.data

import kotlinx.serialization.Serializable

@Serializable
data class Msg<T>(
    val senderName: String,
    val senderId: Int,
    val sendTime: Int,
    val sendTo: Int,
    val senderIcon: String,
    val msgMd5: String,
    val msg: List<T>
)
@Serializable
enum class MsgType {
    Text, Img, Face
}

@Serializable
data class TextMsg(
    val msg: Msg<String>,
    val msgType: MsgType
)

@Serializable
data class ImageMsg(
    val imgBase64: String,
)

@Serializable
data class User(
    val userId: Int,
    val password: String,
    val userName: String,
    val iconUrl: String
)