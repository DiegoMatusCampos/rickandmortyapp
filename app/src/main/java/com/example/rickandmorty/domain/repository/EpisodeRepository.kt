package com.example.rickandmorty.domain.repository

interface EpisodeRepository {
    suspend fun getEpisodes(idEpisodes: List<Int>)
}