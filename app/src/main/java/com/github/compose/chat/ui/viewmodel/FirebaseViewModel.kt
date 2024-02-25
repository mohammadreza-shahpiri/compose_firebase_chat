package com.github.compose.chat.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.compose.chat.base.call.perform
import com.github.compose.chat.data.model.ChatMessage
import com.github.compose.chat.data.model.MessageType
import com.github.compose.chat.data.model.message.FcmObject
import com.github.compose.chat.data.model.message.generateMessage
import com.github.compose.chat.data.repository.FirebaseRepository
import com.github.compose.chat.data.source.UserConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirebaseViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {
    var messageList = mutableStateListOf<ChatMessage>()
        private set

    fun sendTextMessage(content: String) = viewModelScope.launch {
        sendMessage(
            input = generateMessage(
                content = content,
                type = "text",
                token = UserConfig.firebaseToken
            ),
            type = MessageType.TEXT
        )
    }

    fun sendImageMessage(content: String) {
        sendMessage(
            input = generateMessage(
                content = content,
                type = "image",
                token = UserConfig.firebaseToken
            ),
            type = MessageType.IMAGE
        )
    }

    fun sendVoiceMessage(content: String) {
        sendMessage(
            input = generateMessage(
                content = content,
                type = "voice",
                token = UserConfig.firebaseToken
            ),
            type = MessageType.VOICE
        )
    }

    private fun sendMessage(input: FcmObject, type: MessageType) = viewModelScope.launch {
        val message = ChatMessage.Text(
            content = input.message?.data?.content.toString(),
            type = type
        )
        messageList.add(message)
        firebaseRepository.sendMessage(input).perform(
            success = {
                val index = messageList.indexOfFirst { it.uid == message.uid }
                val item = messageList[index] as ChatMessage.Text
                messageList[index] = item.copy(
                    sent = true
                )
            },
            failure = {
                val index = messageList.indexOfFirst { it.uid == message.uid }
                val item = messageList[index] as ChatMessage.Text
                messageList[index] = item.copy(
                    sent = false
                )
            }
        )
    }
}