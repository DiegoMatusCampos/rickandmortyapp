package com.example.rickandmorty.presentation.episode_list

import com.example.rickandmorty.domain.util.NetworkError

sealed interface EpisodeListEvent {

    data class Error(
        val message: NetworkError
    ): EpisodeListEvent
}