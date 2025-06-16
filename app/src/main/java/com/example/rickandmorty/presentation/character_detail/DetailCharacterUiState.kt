package com.example.rickandmorty.presentation.character_detail

import com.example.rickandmorty.domain.model.Character

data class DetailCharacterUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val selectedCharacter: Character? = null
)
