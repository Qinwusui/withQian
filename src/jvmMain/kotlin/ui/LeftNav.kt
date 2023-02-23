package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import theme.menuBarBackGroundColor

@Composable
fun leftNav(
    changeLeftBar: Boolean
) {
    Row(modifier = Modifier.fillMaxSize()) {
        //侧边栏
        AnimatedVisibility(changeLeftBar) {
            Column(
                modifier = Modifier
                    .background(menuBarBackGroundColor)
                    .padding(top = 20.dp, start = 20.dp)
                    .fillMaxWidth(0.3f)
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
        }
    }
}

@Composable
fun listItems(

) = Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Start
) {

}