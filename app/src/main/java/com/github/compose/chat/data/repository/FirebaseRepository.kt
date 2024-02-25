package com.github.compose.chat.data.repository

import com.github.compose.chat.base.call.Call
import com.github.compose.chat.data.model.MessageResponse
import com.github.compose.chat.data.model.message.FcmObject
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val httpClient: HttpClient
) : Call() {
    suspend fun sendMessage(bean: FcmObject) = safeCallRemote<MessageResponse> {
        httpClient.post("./messages:send") {
            setBody(bean)
        }
    }
}