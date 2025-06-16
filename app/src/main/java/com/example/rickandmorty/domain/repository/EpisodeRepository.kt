package com.example.rickandmorty.domain.repository

import com.example.rickandmorty.domain.model.Episode
import com.example.rickandmorty.domain.util.NetworkError
import com.example.rickandmorty.domain.util.Result

interface EpisodeRepository {
    suspend fun getEpisodes(idEpisodes: List<Int>): Result<List<Episode>, NetworkError>
    suspend fun  getEpisode(idEpisode: String): Result<List<Episode>, NetworkError >
}