import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import kotlinx.coroutines.delay
import theme.menuBarBackGroundColor
import theme.menuIconColor
import ui.*
import viewmodel.MainViewModel


@OptIn(ExperimentalMaterialApi::class)
fun main() = application {

    val windowState = rememberWindowState()
    var showSplash by remember {
        mutableStateOf(true)
    }
    var showExitDialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(showSplash) {
        delay(2000)
        showSplash = false
    }

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        undecorated = true,
        icon = painterResource("/icon/love_bubble.png"),
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
            Column(modifier = Modifier.fillMaxSize()) {
                menuBar(
                    title = MainViewModel.title,
                    windowState = windowState,
                    textColor = menuIconColor,
                    backgroundColor = menuBarBackGroundColor,
                    menuState = MainViewModel.changeLeftBar,
                    onMenuIconClicked = {
                        MainViewModel.changeLeftBarState()
                    },
                    onExitClicked = {
                        showExitDialog = true
                    }) {
                    textview(text = if (MainViewModel.changeLeftBar) "收起" else "导航", it, 16.sp)
                }
                Row(modifier = Modifier.background(menuIconColor)) {
                    leftNav(MainViewModel.changeLeftBar)
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
    modifier: Modifier = Modifier.height(30.dp).fillMaxWidth()
        .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
    textColor: Color,
    backgroundColor: Color,
    menuState: Boolean,
    onMenuIconClicked: () -> Unit,
    onExitClicked: () -> Unit,
    menuItem: @Composable RowScope.(Color) -> Unit = {}
) = TopAppBar(modifier = modifier, backgroundColor = backgroundColor) {
    WindowDraggableArea {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onMenuIconClicked) {
                    Crossfade(targetState = menuState) {
                        if (it) {
                            icon(Icons.Default.ArrowBack)
                        } else {
                            icon(Icons.Default.Menu)
                        }
                    }
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




