package com.example.rickandmorty.presentation.character_detail


sealed interface DetailCharacterListAction {
    data object onNavigateUp : DetailCharacterListAction
    data class onClickShowEpisodes(val ids: List<Int>) : DetailCharacterListAction
}