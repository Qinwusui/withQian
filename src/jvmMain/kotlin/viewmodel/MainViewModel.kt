package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Compose Multi Platform 不支持使用ViewModel，故使用单例类实现
 */
object MainViewModel {
    var changeLeftBar by mutableStateOf(false)
        private set

    //切换左侧栏
    fun changeLeftBarState() {
        changeLeftBar = !changeLeftBar
    }
}