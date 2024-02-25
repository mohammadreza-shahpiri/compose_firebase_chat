package com.github.compose.chat.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.compose.chat.data.model.DataEvent
import com.github.compose.chat.data.model.DialogType
import com.github.compose.chat.data.model.Event
import com.github.compose.chat.data.model.EventType
import com.github.compose.chat.data.model.HomeChatItem
import com.github.compose.chat.data.model.uistate.MainUiState
import com.github.compose.chat.data.model.ToastData
import com.github.compose.chat.data.source.DbManager
import com.github.compose.chat.data.source.UserConfig
import com.github.compose.chat.navigation.NavDirect
import com.github.compose.chat.navigation.NavTarget
import com.github.compose.chat.navigation.toDir
import com.github.compose.chat.utils.updateOrCreate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val dbManager: DbManager
) : ViewModel() {
    var uiState by mutableStateOf(MainUiState())
        private set
    private val _mainStateFlow = MutableSharedFlow<Event>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val mainStateFlow = _mainStateFlow.asSharedFlow()

    //////////////////////////////////TOAST//////////////////////////////////////////////
    fun showToast(data: ToastData) = viewModelScope.launch {
        _mainStateFlow.tryEmit(
            DataEvent(
                type = EventType.Toast,
                data = data
            )
        )
    }

    fun showErrorToast(message: String?) = viewModelScope.launch {
        if (message.isNullOrBlank()) return@launch
        showToast(
            ToastData(
                DialogType.FAIL, message
            )
        )
    }

    fun showInfoToast(message: String?) = viewModelScope.launch {
        if (message.isNullOrBlank()) return@launch
        showToast(
            ToastData(
                DialogType.INFO, message
            )
        )
    }

    fun showSuccessToast(message: String?) = viewModelScope.launch {
        if (message.isNullOrBlank()) return@launch
        showToast(
            ToastData(
                DialogType.SUCCESS, message
            )
        )
    }

    fun showWarningToast(message: String?) = viewModelScope.launch {
        if (message.isNullOrBlank()) return@launch
        showToast(
            ToastData(
                DialogType.WARNING, message
            )
        )
    }

    ////////////////////////////////////NAVIGATION////////////////////////////////////////////
    fun navigateTo(navTarget: NavTarget) {
        _mainStateFlow.tryEmit(
            DataEvent(
                type = EventType.Navigation,
                data = navTarget.toDir()
            )
        )
    }

    fun navigateTo(navDir: NavDirect) {
        _mainStateFlow.tryEmit(
            DataEvent(
                type = EventType.Navigation,
                data = navDir
            )
        )
    }

    fun popBackStack(navTarget: NavTarget?) {
        if (navTarget != null) {
            _mainStateFlow.tryEmit(
                DataEvent(
                    type = EventType.Navigation,
                    data = navTarget.toDir().copy(
                        popBackStack = true,
                        replace = false
                    )
                )
            )
        }
    }

    fun navigateUp() {
        _mainStateFlow.tryEmit(
            DataEvent(
                type = EventType.Navigation,
                data = NavTarget.Up.toDir()
            )
        )
    }

    fun loadChats() = viewModelScope.launch {
        updateState(
            uiState.copy(
                loading = true
            )
        )
        dbManager.loadAllChats(
            onLoaded = {
                updateState(
                    uiState.copy(
                        loading = false,
                        chatItems = it.toMutableStateList()
                    )
                )
            },
            onError = {
                updateState(
                    uiState.copy(
                        loading = false,
                        hasError = true,
                        error = it
                    )
                )
            }
        )
    }

    fun addChatItem(chatItem: HomeChatItem) {
        uiState = uiState.copy(
            chatItems = uiState.chatItems.updateOrCreate(
                condition = {
                    it.user.userId == chatItem.user.userId
                },
                foundedItem = {
                    if (it != null) {
                        chatItem.copy(
                            unreadCount = it.unreadCount + 1
                        )
                    } else {
                        chatItem
                    }
                }
            )
        )
    }

    fun toggleTheme() {
        UserConfig.isLight = UserConfig.isLight.not()
        uiState = uiState.copy(
            isLight = UserConfig.isLight
        )
    }

    fun changeTab(index: Int) {
        uiState = uiState.copy(
            currentTabIndex = index
        )
    }

    fun updateState(state: MainUiState) {
        this.uiState = state
    }
}