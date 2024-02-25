package com.github.compose.chat.utils.connectivity

sealed class ConnectionState {
    data object Available : ConnectionState()
    data object Unavailable : ConnectionState()
}

fun ConnectionState.isAvailable() = this is ConnectionState.Available