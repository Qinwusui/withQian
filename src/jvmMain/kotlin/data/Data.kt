package data

data class Msg(
    val senderName: String,
    val senderId: Int,
    val sendTime: String,
    val sendTo: Int,
    val iconUrl: String
)

data class User(
    val userId: Int,
    val password: String,
    val userName: String,
    val iconUrl: String
)