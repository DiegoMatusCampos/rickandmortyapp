package com.example.rickandmorty.presentation.episode_list

import com.example.rickandmorty.domain.model.Episode

data class EpisodeUiState(
    val isError: Boolean = false,
    val episodes: List<Episode> = listOf<Episode>(),
    val seasons: List<String> = listOf<String>(),
    val selectedSeasonIndex: Int = 0,
    val isLoading: Boolean = false
)
