@file:OptIn(ExperimentalPagingApi::class)

package com.example.rickandmorty.data.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickandmorty.data.database.CharacterDao
import com.example.rickandmorty.data.database.CharacterDatabase
import com.example.rickandmorty.data.database.CharacterEntity
import com.example.rickandmorty.data.mappers.toCharacterEntity
import javax.inject.Inject


class CharacterRemoteMediator @Inject constructor(
    private val apiService: ApiService,
    private val database: CharacterDatabase
) : RemoteMediator<Int, CharacterEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {

      return try {
          val page = when(loadType){
              LoadType.REFRESH -> 1
              LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
              LoadType.APPEND -> {
                  val lastItem = state.lastItemOrNull()
                  lastItem?.id ?: return MediatorResult.Success(endOfPaginationReached = true)
              }
          }
          val response = apiService.getCharacters(page)
          database.withTransaction {
              if(loadType == LoadType.REFRESH){
                  database.characterDao().deleteAllCharacters()
              }
              database.characterDao().upsert(response.body()?.results?.map { it.toCharacterEntity() }?: listOf())
          }

          MediatorResult.Success(endOfPaginationReached = response.body()?.results?.isEmpty() ?: true)
      } catch (exception: Exception) {
          MediatorResult.Error(exception)
      }

    }
}