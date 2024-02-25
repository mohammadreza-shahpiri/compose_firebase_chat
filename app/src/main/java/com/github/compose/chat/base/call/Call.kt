package com.github.compose.chat.base.call

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

abstract class Call {
    /**
     * Perform Suspend Call On IO Dispatchers And return Result as Success or Failure
     */
    suspend inline fun <reified T> safeCallRemote(
        crossinline apiCall: suspend () -> HttpResponse
    ): Flow<CallState<T>> = flow {
        emit(CallState.Loading)
        emit(CallState.Success(apiCall().body<T>()))
    }.catch { e ->
        emit(CallState.Failure(Exception(e)))
    }.flowOn(Dispatchers.IO)

    suspend fun <T> safeCallLocal(
        apiCall: suspend () -> T
    ): CallState<T> {
        return withContext(Dispatchers.IO) {
            try {
                CallState.Success(apiCall())
            } catch (throwable: Throwable) {
                CallState.Failure(Exception(throwable))
            }
        }
    }
}
