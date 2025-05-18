package com.example.rickandmorty.domain.repository
import androidx.paging.PagingData
import com.example.rickandmorty.domain.util.Result

import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.util.NetworkError
import kotlinx.coroutines.flow.Flow


interface CharacterRepository {
   fun getAllCharacters(): Flow<PagingData<Character>>
    suspend fun getCharacters(id: Int): Result<Character, NetworkError>
}