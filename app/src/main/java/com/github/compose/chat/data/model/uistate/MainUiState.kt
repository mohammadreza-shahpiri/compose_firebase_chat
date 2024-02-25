package com.github.compose.chat.data.model.uistate

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.compose.chat.data.model.HomeChatItem
import com.github.compose.chat.data.source.UserConfig


data class MainUiState(
    val loading: Boolean = false,
    val hasError: Boolean = false,
    val error: String = "",
    val isLight: Boolean = UserConfig.isLight,
    val chatItems: SnapshotStateList<HomeChatItem> = mutableStateListOf(),
    val currentTabIndex: Int = 0
)
