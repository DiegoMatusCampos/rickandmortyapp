package com.example.rickandmorty.presentation.character_list

import com.example.rickandmorty.domain.model.Character

data class CharacterUiState(
    val characters: List<Character> = emptyList<Character>(),
    val isLoading: Boolean = false,
    val selectedCharacter: Character? = null
)
