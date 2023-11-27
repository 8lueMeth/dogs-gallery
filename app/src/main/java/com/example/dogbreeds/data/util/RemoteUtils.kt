package com.example.dogbreeds.data.util

import com.example.dogbreeds.network.error.ConnectivityError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import kotlin.coroutines.CoroutineContext

suspend fun <T> runCatchingWithContext(
    context: CoroutineContext = Dispatchers.IO,
    block: suspend CoroutineScope.() -> T,
): Result<T> {
    return withContext(context) {
        runCatching { block() }
    }
}

fun <F : Any, R : Response<F>, T : Any> Result<R>.result(mapper: (F) -> T): Result<T> {
    return if (isSuccess) {
        val response = getOrThrow()
        return if (response.isSuccessful) {
            Result.success(mapper(response.body()!!))
        } else {
            Result.failure(Exception())
        }
    } else {
        val exception = handleException()
        Result.failure(exception)
    }
}

fun <T : Any, R : Response<T>> Result<R>.result(): Result<T> {
    return if (isSuccess) {
        val response = getOrThrow()
        return if (response.isSuccessful) {
            Result.success(response.body()!!)
        } else {
            Result.failure(Exception())
        }
    } else {
        val exception = handleException()
        Result.failure(exception)
    }
}

private fun Result<Any>.handleException(): Throwable {
    return if (exceptionOrNull() is UnknownHostException || exceptionOrNull() is TimeoutException) {
        ConnectivityError()
    } else UnknownError()
}