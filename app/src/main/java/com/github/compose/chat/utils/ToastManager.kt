package com.github.compose.chat.utils

import com.github.compose.chat.data.model.DialogType
import com.github.compose.chat.data.model.ToastData
import com.github.compose.chat.utils.event.ConsumableEvent
import com.github.compose.chat.utils.event.LiveDataBus

object ToastManager {
    private fun showToast(data: ToastData) {
        LiveDataBus.publish(
            "toast",
            ConsumableEvent(value =  data)
        )
    }
    fun showErrorToast(message: String?){
        if (message.isNullOrBlank()) return
        showToast(
            ToastData(
                DialogType.FAIL, message
            )
        )
    }

    fun showInfoToast(message: String?) {
        if (message.isNullOrBlank()) return
        showToast(
            ToastData(
                DialogType.INFO, message
            )
        )
    }

    fun showSuccessToast(message: String?){
        if (message.isNullOrBlank()) return
        showToast(
            ToastData(
                DialogType.SUCCESS, message
            )
        )
    }

    fun showWarningToast(message: String?){
        if (message.isNullOrBlank()) return
        showToast(
            ToastData(
                DialogType.WARNING, message
            )
        )
    }
}