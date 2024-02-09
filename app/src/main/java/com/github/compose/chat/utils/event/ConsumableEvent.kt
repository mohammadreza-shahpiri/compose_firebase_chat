package com.github.compose.chat.utils.event

data class ConsumableEvent(
    var isConsumed: Boolean = false,
    var value: Any? = null
) {
    fun runAndConsume(runnable: () -> Unit) {
        if (!isConsumed) {
            runnable()
            isConsumed = true
        }
    }
}