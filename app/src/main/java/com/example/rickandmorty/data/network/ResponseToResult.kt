package com.example.rickandmorty.data.network
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.util.NetworkError
import retrofit2.Response
import com.example.rickandmorty.domain.util.Result

suspend inline fun <reified T> reponseToResult(
    reponse: Response<T>
): Result<T, NetworkError> {
    return when(reponse.code()){
        in 200..299 -> {
            Result.Success(reponse.body()?: listOf<Character>())}
        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
        429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
        else -> Result.Error(NetworkError.UNKNOWN)
    } as Result<T, NetworkError>
}