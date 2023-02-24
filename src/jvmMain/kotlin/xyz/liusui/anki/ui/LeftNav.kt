package xyz.liusui.anki.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import xyz.liusui.anki.theme.IconLoveBubble
import xyz.liusui.anki.theme.menuBarBackGroundColor
import xyz.liusui.anki.theme.menuIconColor

@Composable
fun leftNav() {
    Row(modifier = Modifier.fillMaxSize()) {
        //侧边栏
        //左侧图标栏
        Column(
            modifier = Modifier.background(menuBarBackGroundColor)
                .padding(top = 20.dp)
                .fillMaxWidth(0.05f)
                .fillMaxHeight()
        ) {
            LazyColumn {
                item {
                    IconButton(onClick = {

                    }) {
                        image(painter = painterResource(IconLoveBubble))
                    }
                }
            }
        }
        //左侧消息栏
        Column(
            modifier = Modifier
                .background(menuIconColor)
                .padding(top = 20.dp, start = 20.dp)
                .fillMaxWidth(0.2f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                item {
                    listItems()
                }
            }
        }
        Divider(modifier = Modifier.fillMaxHeight().width(1.dp), color = menuBarBackGroundColor)
    }
}

@Composable
fun listItems(

) = Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Start
) {

}