package com.github.compose.chat.data.model

data class HomeChatItem(
    val user: UserInfoModel,
    val content: ChatMessage,
    val unreadCount: Int,
    val time: String
)
