package com.github.compose.chat.data.model

import androidx.compose.runtime.Immutable
import java.util.UUID

@Immutable
enum class MessageType {
    TEXT, VOICE, IMAGE;

    companion object {
        fun String?.toMessageType(): MessageType {
            return when (this?.lowercase()) {
                "voice" -> VOICE
                "image" -> IMAGE
                else -> TEXT
            }
        }
    }
}

@Immutable
sealed class ChatMessage {
    abstract val time: Long
    abstract val isFromMe: Boolean
    abstract val sent: Boolean
    abstract val read: Boolean
    abstract val type: MessageType
    val uid: UUID = UUID.randomUUID()

    @Immutable
    data class Text(
        override val time: Long = System.currentTimeMillis(),
        override val isFromMe: Boolean = true,
        override val sent: Boolean = false,
        override val read: Boolean = false,
        override val type: MessageType = MessageType.TEXT,
        val content: String
    ) : ChatMessage()

    @Immutable
    data class Voice(
        override val time: Long = System.currentTimeMillis(),
        override val isFromMe: Boolean = true,
        override val sent: Boolean = false,
        override val read: Boolean = false,
        override val type: MessageType,
        val content: ByteArray
    ) : ChatMessage() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            return uid == (other as Voice).uid
        }

        override fun hashCode(): Int {
            return time.hashCode()
        }
    }

    @Immutable
    data class Image(
        override val time: Long = System.currentTimeMillis(),
        override val isFromMe: Boolean = true,
        override val sent: Boolean = false,
        override val read: Boolean = false,
        override val type: MessageType,
        val content: ByteArray
    ) : ChatMessage() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            return uid == (other as Image).uid
        }

        override fun hashCode(): Int {
            return time.hashCode()
        }
    }
}