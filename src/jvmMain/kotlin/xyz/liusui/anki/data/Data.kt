package xyz.liusui.anki.data

import kotlinx.serialization.Serializable

@Serializable
data class MsgData(
    val msgIndex: Int?,
    val receiverName: String?,
    val receiverId: Int?,
    val senderName: String,
    val senderId: Int,
    val senderIconUrl: String,
    val roomId: Int?,
    val roomName: String?,
    val msg: String,
    val t: String,
)

@Serializable
data class MsgDataBase(
    val msgIndex: Int?,
    val nickName: String,
    val from: String,
    val chatRoomId: Int?,
    val chatRoomName: String?,
    val to: String?,
    val toNickName: String?,
    val msg: String,
    val time: String,
)

@Serializable
data class User(
    val userId: Int,
    val password: String,
    val userName: String,
    val iconUrl: String
)

enum class ScreenPage {
    MessageScreen, ContactScreen, GroupScreen
}