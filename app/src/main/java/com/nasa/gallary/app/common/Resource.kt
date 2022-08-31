package com.nasa.gallary.app.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error(val exception: Throwable) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

inline fun <T, R> Resource<T>.map(transform: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Error -> Resource.Error(exception)
        is Resource.Loading -> Resource.Loading
    }
}

fun <T> Flow<Resource<T>>.doOnSuccess(action: suspend (T) -> Unit): Flow<Resource<T>> =
    transform { value ->
        if (value is Resource.Success) {
            action(value.data)
        }
        return@transform emit(value)
    }

fun <T> Flow<Resource<T>>.doOnStatusChanged(action: suspend (Status) -> Unit): Flow<Resource<T>> =
    transform { value ->
        when (value) {
            is Resource.Success -> action(Status.Content)
            is Resource.Error -> action(Status.Error(value.exception))
            Resource.Loading -> action(Status.Loading)
        }
        return@transform emit(value)
    }
