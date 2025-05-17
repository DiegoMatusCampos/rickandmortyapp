package com.example.rickandmorty.domain.util

typealias DomainError = Error

sealed interface Result<out D, out E: Error>{
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E: DomainError>(val error: E): Result<Nothing, E>
}

inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

inline fun <T, E:Error> Result<T,E>.onSucces(action: (T) -> Unit): Result<T,E> {
    return when(this){
        is Result.Error<E> -> this
        is Result.Success<T> -> {
            action(data)
            this
        }
    }
}

inline  fun <T, E: Error>  Result<T,E>.onError(action: (E) -> Unit): Result<T,E> {
    return when(this){
        is Result.Error<E> -> {
            action(error)
            this
        }
        is Result.Success<T> -> this
    }
}