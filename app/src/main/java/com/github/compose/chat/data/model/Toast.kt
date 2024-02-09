package com.github.compose.chat.data.model

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Immutable

@Immutable
enum class DialogType {
    SUCCESS, FAIL, INFO, WARNING
}

@Immutable
data class ToastData(
    val type: DialogType,
    override val message: String,
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val withDismissAction: Boolean = false
) : SnackbarVisuals