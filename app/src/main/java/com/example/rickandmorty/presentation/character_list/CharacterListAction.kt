package com.example.rickandmorty.presentation.character_list

import com.example.rickandmorty.domain.model.Character

sealed interface CharacterListAction {
    data class onClickListItem(val character: Character) : CharacterListAction
    data object onNavigateUp : CharacterListAction
}