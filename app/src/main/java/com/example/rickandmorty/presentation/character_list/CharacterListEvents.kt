package com.example.rickandmorty.presentation.character_list

import com.example.rickandmorty.domain.util.NetworkError

sealed interface CharacterListEvents {
    data class Error(val error: NetworkError) : CharacterListEvents
}