package com.github.compose.chat.data.model.message


data class FcmObject (
    val message: Message? = null
)


data class Message (
    val token: String? = null,
    val data: Data? = null,
    val android: Android? = null
)


data class Data (
    val type: String? = null,
    var content: String? = null
)


data class Android (
    val priority: String? = null
)

fun generateMessage(
    content: String?,
    type: String?,
    token: String?
): FcmObject {
    /////////////////////////////////////
    val data = Data(
        type=type,content=content
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