package com.example.rickandmorty.presentation.episode_list

sealed interface EpisodeListAction {
    data class onSelectedSeasonFilter(val index: Int) : EpisodeListAction
}