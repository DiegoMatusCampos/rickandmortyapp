package com.example.rickandmorty.presentation.character_detail

import com.example.rickandmorty.domain.util.NetworkError

sealed interface DetailCharacterEvents {
    data class Error( val message: NetworkError) : DetailCharacterEvents
}