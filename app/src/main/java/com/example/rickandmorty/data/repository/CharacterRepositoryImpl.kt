@file:OptIn(ExperimentalPagingApi::class)

package com.example.rickandmorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.rickandmorty.domain.util.Result
import com.example.rickandmorty.data.database.CharacterDao
import com.example.rickandmorty.data.database.CharacterDatabase
import com.example.rickandmorty.data.database.CharacterEntity
import com.example.rickandmorty.data.mappers.toCharacter
import com.example.rickandmorty.data.mappers.toCharacterEntity
import com.example.rickandmorty.data.network.ApiService
import com.example.rickandmorty.data.network.CharacterRemoteMediator
import com.example.rickandmorty.data.network.safeCall
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.repository.CharacterRepository
import com.example.rickandmorty.domain.util.NetworkError
import com.example.rickandmorty.domain.util.map
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class CharacterRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val database: CharacterDatabase
) : CharacterRepository {
    override fun getAllCharacters(): Flow<PagingData<Character>>{
        val pagingSourceFactory = { database.characterDao().getAllCharacters()}

        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = CharacterRemoteMediator(api, database),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { it.toCharacter() }
        }
    }

    override suspend fun getCharacters(id: Int): Result<Character, NetworkError> {
        TODO()
    }
}