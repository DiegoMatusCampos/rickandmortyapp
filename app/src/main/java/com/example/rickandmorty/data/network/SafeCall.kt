package com.example.rickandmorty.data.network

import com.example.rickandmorty.domain.util.NetworkError
import retrofit2.Response
import java.nio.channels.UnresolvedAddressException
import com.example.rickandmorty.domain.util.Result
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> safeCall(
    execute: () -> Response<T>
): Result<T, NetworkError> {

    val response =  try {
            execute()
    } catch (e: UnresolvedAddressException){
        e.printStackTrace()
        return Result.Error(NetworkError.NO_INTERNET)
    }catch (e: SerializationException){
        e.printStackTrace()
        return Result.Error(NetworkError.SERIALIZATION)
    }catch (e: Exception){
        e.printStackTrace()
        return Result.Error(NetworkError.UNKNOWN)
    }

    return reponseToResult(response)

}