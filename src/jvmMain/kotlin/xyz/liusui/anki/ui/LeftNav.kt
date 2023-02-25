package xyz.liusui.anki.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import xyz.liusui.anki.data.MsgData
import xyz.liusui.anki.data.ScreenPage
import xyz.liusui.anki.repo.MsgRepo
import xyz.liusui.anki.theme.*
import xyz.liusui.anki.utils.AsyncImage
import xyz.liusui.anki.utils.loadImageBitmap

@Composable
fun leftIconBar(
    onIconClick: (ScreenPage) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(0.05f)) {
        //侧边栏
        //左侧图标栏
        Column(
            modifier = Modifier.background(menuBarBackGroundColor)
                .padding(top = 20.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = {
                //TODO
                onIconClick(ScreenPage.MessageScreen)
            }) {
                Column {
                    image(painter = painterResource(IconMsgBubble))
                    textview("消息", menuIconColor, 12.sp)
                }

            }
            Divider(
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.5f).height(1.dp),
                color = menuIconColor.copy(alpha = 0.5f)
            )
            IconButton(onClick = {
                //TODO
                onIconClick(ScreenPage.ContactScreen)
            }) {
                //切换好友页面
                Column {
                    image(painter = painterResource(IconFriends))
                    textview("好友", menuIconColor, 12.sp)
                }
            }
            Divider(
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(0.5f).height(1.dp),
                color = menuIconColor.copy(alpha = 0.5f)
            )
            IconButton(onClick = {
                //切换群组页面
                //TODO
                onIconClick(ScreenPage.GroupScreen)
            }) {
                Column {
                    image(painter = painterResource(IconContacts))
                    textview("群组", menuIconColor, 12.sp)
                }
            }

        }

    }

}

@Composable
fun messageList(
    onMsgIconClick: () -> Unit
) {
    val list by MsgRepo.querySimpleMsgList().collectAsState(mutableListOf())
    if (list.isEmpty()){
        Column(
            modifier = Modifier
                .background(menuIconColor)
                .fillMaxHeight()
                .fillMaxWidth(0.25f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = menuBarBackGroundColor)
        }
    }else{
        Column(
            modifier = Modifier
                .background(menuIconColor)
                .fillMaxHeight()
                .fillMaxWidth(0.25f),
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(list) { i, msg ->
                    if (i != 0 && i != list.size) {
                        Divider(
                            modifier = Modifier
                                .height(1.dp)
                                .align(Alignment.CenterHorizontally),
                            color = menuBarBackGroundColor
                        )
                    }
                    listItem(msg, onIconClick = onMsgIconClick)
                }
            }
        }
    }

}

@Composable
fun listItem(
    msgData: MsgData,
    onIconClick: () -> Unit
) {
    val density = LocalDensity.current
    Row(modifier = Modifier
        .fillMaxSize()
        .clickable {

        }
        .padding(horizontal = 10.dp)
    ) {
        //头像展示
        IconButton(onClick = {
            onIconClick()
        }, modifier = Modifier.fillMaxWidth(0.2f)) {
            Image(loadImageBitmap(msgData.senderIconUrl), contentDescription = null)
        }
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            textview(msgData.senderName, menuBarBackGroundColor, 20.sp)
            textview(msgData.msg, menuBarBackGroundColor.copy(alpha = 0.6f), 14.sp)
        }

    }
}