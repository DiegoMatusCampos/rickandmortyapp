package com.example.rickandmorty.presentation.character_detail

import com.example.rickandmorty.domain.model.Character

sealed interface DetailCharacterListAction {
    data object onNavigateUp : DetailCharacterListAction
}