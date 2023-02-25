package xyz.liusui.anki.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import xyz.liusui.anki.data.MsgData
import xyz.liusui.anki.utils.logD
import xyz.liusui.anki.utils.logE
import xyz.liusui.anki.utils.logI
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

object MsgRepo {
    private const val JDBC_URL = "jdbc:sqlite:./msg.db"

    init {
        Class.forName("org.sqlite.JDBC")
        createDataBase()
    }

    //数据库创建
    private fun createDataBase() {
        val created = useDataBasePool {
            it.execute(
                """
CREATE TABLE msg_db
(
    msg_index       integer not null
        constraint msg_pk
            primary key autoincrement,
    receiver_name   TEXT    not null,
    receiver_id     integer not null,
    sender_name     TEXT    not null,
    sender_id       integer not null,
    sender_icon_url TEXT    not null,
    room_id         integer not null,
    room_name       TEXT    not null,
    msg             TEXT    not null,
    t               TEXT    not null
)
""".trimIndent()
            )
        }
        if (created) {
            "已经创建过数据库".logD()
        } else {
            "创建数据库成功".logI()
        }
    }

    //数据库查询
    fun queryAll() = flowByIO {
        val list = mutableListOf<MsgData>()
        useDataBasePool {
            val res = it.executeQuery(
                """
            select * from msg_db
        """.trimIndent()
            )
            res.addToList(list)
        }
        list
    }


    //数据库条件查询
    fun querySimpleMsgList() = flowByIO {
        val list = mutableListOf<MsgData>()
        useDataBasePool {
            val res = it.executeQuery(
                """
                select distinct
                receiver_name,
                receiver_id,
                sender_name,
                sender_id,
                sender_icon_url,
                room_id,
                room_name,
                msg,
                t
                from msg_db
            """.trimIndent()
            )
            res.addToList(list)
        }
        list
    }
    fun queryByGroup()= flowByIO {

    }
    private fun ResultSet.addToList(list: MutableList<MsgData>) {
        while (this.next()) {
            list.add(
                MsgData(
                    msgIndex = null,
                    receiverId = this.getInt("receiver_id"),
                    receiverName = this.getString("receiver_name"),
                    senderId = this.getInt("sender_id"),
                    senderName = this.getString("sender_name"),
                    senderIconUrl = this.getString("sender_icon_url"),
                    roomId = this.getInt("room_id"),
                    roomName = this.getString("room_name"),
                    msg = this.getString("msg"),
                    t = this.getString("t")
                )
            )
        }
    }

    //写入数据库
    fun insertMsgToDb(msgData: MsgData) = useDataBasePool {
        it.executeUpdate(
            """
                insert into msg_db values (${msgData.msgIndex},
                '${msgData.receiverName}',
                '${msgData.receiverId}',
                '${msgData.senderName}',
                '${msgData.senderId}',
                '${msgData.senderIconUrl}',
                '${msgData.roomId}',
                '${msgData.roomName}',
                "${msgData.msg}",
                '${msgData.t}'
)
            """.trimIndent()
        )
    }


    //将消息按照联系人和群组进行区分
    //通用Flow函数
    fun <T> flowByIO(block: () -> T) = flow {
        try {
            emit(block())
        } catch (e: Exception) {
            e.logE()
        }
    }.flowOn(Dispatchers.IO)

    //数据库公共函数
    private fun useDataBasePool(content: (statement: Statement) -> Unit): Boolean {
        val driverManager = DriverManager.getConnection(JDBC_URL)
        driverManager.autoCommit = false
        val stmt = driverManager.createStatement()
        return try {
            stmt.apply {
                content(stmt)
            }
            driverManager.commit()
            stmt.close()
            driverManager.close()
            true
        } catch (e: Exception) {
            e.logE()
            false
        }
    }
}