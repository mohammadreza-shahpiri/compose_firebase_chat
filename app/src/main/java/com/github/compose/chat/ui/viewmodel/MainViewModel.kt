package com.github.compose.chat.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.compose.chat.data.model.DataEvent
import com.github.compose.chat.data.model.DialogType
import com.github.compose.chat.data.model.Event
import com.github.compose.chat.data.model.EventType
import com.github.compose.chat.data.model.ToastData
import com.github.compose.chat.navigation.NavDirect
import com.github.compose.chat.navigation.NavTarget
import com.github.compose.chat.navigation.toDir
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _mainStateFlow = MutableSharedFlow<Event>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val mainStateFlow = _mainStateFlow.asSharedFlow()

    //////////////////////////////////TOAST//////////////////////////////////////////////
    private fun showToast(data: ToastData) = viewModelScope.launch {
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
}