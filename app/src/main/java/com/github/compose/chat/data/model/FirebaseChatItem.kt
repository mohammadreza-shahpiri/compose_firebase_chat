package com.github.compose.chat.data.model

data class FirebaseChatItem(
    val id: String? = null,
    val user: UserInfoModel,
    val content: Any = "",
    val type: String = "",
    val read: Boolean = false,
    val date: Long = 0
)
