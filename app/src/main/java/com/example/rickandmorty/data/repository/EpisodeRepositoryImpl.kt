package com.example.rickandmorty.data.repository

import com.example.rickandmorty.data.dto.ResponseDto
import com.example.rickandmorty.data.dto.ResultEpisodeDto
import com.example.rickandmorty.data.mappers.toEpisode
import com.example.rickandmorty.data.network.ApiService
import com.example.rickandmorty.data.network.safeCall
import com.example.rickandmorty.domain.model.Episode
import com.example.rickandmorty.domain.repository.EpisodeRepository
import com.example.rickandmorty.domain.util.NetworkError
import com.example.rickandmorty.domain.util.Result
import com.example.rickandmorty.domain.util.map
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private  val api: ApiService
): EpisodeRepository {
    override suspend fun getEpisodes(idEpisodes: List<Int>): Result<List<Episode>, NetworkError> {

        val ids = idEpisodes.joinToString(",")

        return if(idEpisodes.size == 1){
            this.getEpisode(idEpisodes[0].toString())
        } else {
            safeCall<List<ResultEpisodeDto>> {
                api.getEpisodes(ids)
            }.map { response ->
                response.map {
                    it.toEpisode()
                }
            }
        }
    }

    override suspend fun getEpisode(idEpisode: String): Result<List<Episode>, NetworkError> {

        return safeCall<ResultEpisodeDto> {
            api.getEpisode(idEpisode)
        }.map { response ->
            listOf<ResultEpisodeDto>(response).map {
                it.toEpisode()
            }
        }
    }
}