package com.example.rickandmorty.data.network


import com.example.rickandmorty.data.dto.ResponseDto
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): Response<ResponseDto>
}