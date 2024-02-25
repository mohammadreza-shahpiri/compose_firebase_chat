package com.github.compose.chat.base.call

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.transform

sealed class CallState<out T> {
    data object Loading : CallState<Nothing>()
    data class Success<T>(val data: T) : CallState<T>()
    data class Failure(val msg: Throwable?) : CallState<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "success $data"
            is Failure -> "Failure ${msg?.message}"
            is Loading -> "Loading"
        }
    }
}

fun CallState<*>?.isLoading() = (this is CallState.Loading)
fun CallState<*>?.isFailure() = (this is CallState.Failure)

suspend fun <T> Flow<CallState<T>>.perform(
    loading: () -> Unit = {},
    success: suspend (T) -> Unit = {},
    failure: suspend (Throwable?) -> Unit = {}
) {
    doOnLoading {
        loading()
    }.doOnSuccess {
        success(it)
    }.doOnFailure {
        failure(it)
    }.collect()
}

inline fun <T> Flow<CallState<T>>.doOnSuccess(crossinline action: suspend (T) -> Unit): Flow<CallState<T>> =
    transform { result ->
        if (result is CallState.Success) {
            action(result.data)
        }
        return@transform emit(result)
    }

inline fun <T> Flow<CallState<T>>.doOnFailure(crossinline action: suspend (Throwable?) -> Unit): Flow<CallState<T>> =
    transform { result ->
        if (result is CallState.Failure) {
            action(result.msg)
        }
        return@transform emit(result)
    }

inline fun <T> Flow<CallState<T>>.doOnLoading(crossinline action: suspend () -> Unit): Flow<CallState<T>> =
    transform { result ->
        if (result is CallState.Loading) {
            action()
        }
        return@transform emit(result)
    }
