package com.github.compose.chat.data.model.message

import kotlinx.serialization.Serializable

@Serializable
data class FcmObject(
    val message: Message? = null
)

@Serializable
data class Message(
    val token: String? = null,
    val data: Data? = null,
    val android: Android? = null
)

@Serializable
data class Data(
    val type: String? = null,
    val content: String? = null
)

@Serializable
data class Android(
    val priority: String? = null
)

fun generateMessage(
    content: String?,
    type: String?,
    token: String?
): FcmObject {
    /////////////////////////////////////
    val data = Data(
        type = type,
        content = content
    )
    //////////////////////////////
    val android = Android(priority = "high")
    /////////////////////////////
    val message = Message(
        data = data,
        android = android,
        token = token
    )
    return FcmObject(message = message)
}