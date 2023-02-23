package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import viewmodel.MainViewModel

@Composable
fun NavScreen(mainViewModel: MainViewModel) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text("WithQian")
        }, modifier = Modifier.fillMaxWidth(), navigationIcon = {
            IconButton(
                onClick = {
                    mainViewModel.changeLeftBarState()
                },
            ) {
                Icon(
                    imageVector = if (mainViewModel.changeLeftBar) Icons.Default.Clear else Icons.Default.Menu,
                    contentDescription = null
                )
            }
        })
    }) {
        Row(modifier = Modifier.fillMaxSize()) {
            //主页面，将主页面划分为左侧栏和右侧栏两个部分，需要使用bool进行展开与收起
            AnimatedVisibility(mainViewModel.changeLeftBar) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.3f).padding(top = 20.dp, start = 20.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    LazyColumn {
                        item {
                            Text("好耶")
                        }
                    }
                }
            }
            Column(modifier = Modifier.fillMaxSize().background(Color.Cyan)) {
                Text("好耶")
            }
        }
    }
}