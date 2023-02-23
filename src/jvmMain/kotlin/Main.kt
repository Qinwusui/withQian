import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import theme.menuBarBackGroundColor
import theme.menuIconColor
import ui.icon
import ui.menuTitle
import ui.textview
import viewmodel.MainViewModel
import kotlin.system.exitProcess


fun main() = application {

    val windowState = rememberWindowState()

    Window(
        onCloseRequest = ::exitApplication, state = windowState, undecorated = true,

        ) {
        Column {
            menuBar(
                "画大饼咯",
                windowState,
                textColor = menuIconColor,
                backgroundColor = menuBarBackGroundColor,
                menuState = MainViewModel.changeLeftBar,
                onMenuIconClicked = {
                    MainViewModel.changeLeftBarState()
                }) {
                textview(text = "菜单", it, 16.sp)
            }
            content(MainViewModel.changeLeftBar)
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
    menuState: Boolean,
    onMenuIconClicked: () -> Unit,
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
                            icon(Icons.Default.Close)
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
                    exitProcess(0)
                }) {
                    icon(imageVector = Icons.Default.Close)
                }
            }
        }

    }
}

@Composable
fun content(
    changeLeftBar: Boolean
) {
    Row(modifier = Modifier.fillMaxSize()) {
        //侧边栏
        AnimatedVisibility(changeLeftBar) {
            Column(
                modifier = Modifier
                    .background(menuIconColor)
                    .padding(top = 20.dp, start = 20.dp)
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start
            ) {
                textview(":好饿呀", menuBarBackGroundColor, 16.sp)
            }
        }
        //右边栏
        Column(modifier = Modifier.fillMaxWidth()) {

        }
    }
}

