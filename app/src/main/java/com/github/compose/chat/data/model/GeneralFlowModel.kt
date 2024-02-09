package com.github.compose.chat.data.model


interface Event{
    val type: EventType
}
sealed interface EventType {
    data object FinishComponent : EventType
    data object Toast : EventType
    data object Navigation : EventType
}

data class DataEvent(
    val data: Any,
    override val type: EventType
) : Event

data class EmptyEvent(
    override val type: EventType
) : Event