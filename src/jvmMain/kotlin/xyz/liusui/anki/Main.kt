package xyz.liusui.anki

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import kotlinx.coroutines.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import xyz.liusui.anki.data.MsgData
import xyz.liusui.anki.data.ScreenPage
import xyz.liusui.anki.repo.Repo
import xyz.liusui.anki.theme.IconLoveBubble
import xyz.liusui.anki.theme.menuBarBackGroundColor
import xyz.liusui.anki.theme.menuIconColor
import xyz.liusui.anki.ui.*
import xyz.liusui.anki.utils.logE

fun main() {
    val scope = CoroutineScope(Dispatchers.IO)
    scope.launch(Dispatchers.IO) {
        val msgData = MsgData(
            msgIndex = 0,
            receiverId = null,
            receiverName = null,
            senderId = 234,
            senderName = "liusui",
            senderIconUrl = "haoye",
            roomId = 0,
            roomName = "好耶",
            msg = "好夜夜夜夜",
            t = "为3312313322"
        )
        val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }
        json.encodeToString(msgData).also {
            it.logE()
        }
    }
    mains()

}

@OptIn(ExperimentalMaterialApi::class)
fun mains() = application {
    val windowState = rememberWindowState()
    var showSplash by remember {
        mutableStateOf(true)
    }
    var showExitDialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(showSplash) {
        delay(1000)
        showSplash = false
    }
    val scope = rememberCoroutineScope()
    scope.launch {
        Repo
    }
    var currentListPage by remember {
        mutableStateOf(ScreenPage.MessageScreen)
    }

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        undecorated = true,
        icon = painterResource(IconLoveBubble),
        transparent = true
    ) {
        AnimatedVisibility(showExitDialog) {
            AlertDialog(
                onDismissRequest = {
                    showExitDialog = false
                },
                title = {
                    textview("确定要退出吗？", menuBarBackGroundColor, 30.sp)
                },
                dismissButton = {
                    TextButton(onClick = {
                        showExitDialog = false
                    }) {
                        textview("取消", menuBarBackGroundColor, 20.sp)
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        exitApplication()
                    }) {
                        textview("退出", menuIconColor, 20.sp)
                    }
                }
            )
        }
        AnimatedVisibility(showSplash) {
            SplashScreen()
        }
        AnimatedVisibility(!showSplash) {
            Column(modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(10.dp))) {
                menuBar(
                    title = "HimaWari",
                    windowState = windowState,
                    textColor = menuIconColor,
                    backgroundColor = menuBarBackGroundColor,
                    onMenuIconClicked = {

                    },
                    onExitClicked = {
                        showExitDialog = true
                    }) {
                    textview(text = "导航", it, 16.sp)
                }
                Row(modifier = Modifier.background(menuIconColor)) {

                    //怎么实现点击icon切换右侧消息页？
                    leftIconBar {
                        currentListPage = it
                    }
                    when (currentListPage) {
                        ScreenPage.MessageScreen -> {
                            messageList {

                            }
                        }

                        ScreenPage.ContactScreen -> {

                        }

                        ScreenPage.GroupScreen -> {

                        }
                    }
                    Divider(modifier = Modifier.fillMaxHeight().width(1.dp), color = menuBarBackGroundColor)
                    rightMainView(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}


@Composable
fun WindowScope.menuBar(
    title: String,
    windowState: WindowState,
    modifier: Modifier = Modifier.height(30.dp).fillMaxWidth(),
    textColor: Color,
    backgroundColor: Color,
    onMenuIconClicked: () -> Unit,
    onExitClicked: () -> Unit,
    menuItem: @Composable RowScope.(Color) -> Unit = {}
) = TopAppBar(modifier = modifier, backgroundColor = backgroundColor) {
    WindowDraggableArea(modifier = Modifier.pointerInput(Unit) {
        detectTapGestures(
            //顶栏双击事件处理
            onDoubleTap = {
                windowState.placement =
                    if (windowState.placement == WindowPlacement.Maximized) WindowPlacement.Floating else WindowPlacement.Maximized
            },
        )

    }) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onMenuIconClicked) {
                        icon(Icons.Default.Menu)
                    }
                    menuItem(textColor)
                }
                menuTitle(title)
                //右侧三大金刚键
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    //最小化
                    IconButton(onClick = { windowState.isMinimized = true }) {
                        icon(imageVector = Icons.Default.ArrowDropDown)
                    }
                    //最大化&窗口
                IconButton(onClick = {
                    windowState.placement =
                        if (windowState.placement == WindowPlacement.Maximized) WindowPlacement.Floating else WindowPlacement.Maximized
                }) {
                    icon(imageVector = Icons.Default.Add)
                }
                //关闭窗口
                IconButton(onClick = {
                    onExitClicked()
                }) {
                    icon(imageVector = Icons.Default.Close)
                }
            }
        }

    }
}




