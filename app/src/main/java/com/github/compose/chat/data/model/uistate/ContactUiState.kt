package com.github.compose.chat.data.model.uistate

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.compose.chat.data.model.UserInfoModel

data class ContactUiState(
    val loading: Boolean = false,
    val hasError: Boolean = false,
    val showAddDialog: Boolean = false,
    val error: String = "",
    val contacts: SnapshotStateList<UserInfoModel> = mutableStateListOf(),
)
