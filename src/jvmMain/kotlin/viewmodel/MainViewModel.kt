package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Compose Multi Platform 不支持使用ViewModel，故使用单例类实现
 */
object MainViewModel {
    private val scope = CoroutineScope(Dispatchers.Default).coroutineContext
    var changeLeftBar by mutableStateOf(false)
        private set
    var title by mutableStateOf("HimaWari")
    var showSplash by mutableStateOf(true)
    var messageLoading = MutableStateFlow(false)

    //切换左侧栏
    fun changeLeftBarState() {
        changeLeftBar = !changeLeftBar
    }


}