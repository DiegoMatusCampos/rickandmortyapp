package com.example.rickandmorty.data.network


import com.example.rickandmorty.data.dto.ResponseDto
import com.example.rickandmorty.data.dto.ResultDto
import com.example.rickandmorty.data.dto.ResultEpisodeDto
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): Response<ResponseDto<ResultDto>>

    @GET("api/character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ) : Response<ResultDto>

    @GET("api/episodes/{ids}")
    suspend fun getEpisodes(
        @Path("ids") ids : String,
    ): Response<ResponseDto<ResultEpisodeDto>>
}