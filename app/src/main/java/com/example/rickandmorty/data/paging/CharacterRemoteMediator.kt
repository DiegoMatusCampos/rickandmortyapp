package com.example.rickandmorty.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickandmorty.data.database.RickAndMortyDatabase
import com.example.rickandmorty.data.database.character.CharacterEntity
import com.example.rickandmorty.data.mappers.toCharacterEntity
import com.example.rickandmorty.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import java.nio.channels.UnresolvedAddressException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ExperimentalPagingApi
class CharacterRemoteMediator @Inject constructor(
    private val apiService: ApiService,
    private val database: RickAndMortyDatabase
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
                  if (lastItem == null) return MediatorResult.Success(true)
                  (lastItem.id / state.config.pageSize) + 1
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
      } catch (e: UnresolvedAddressException){
          e.printStackTrace()
          return MediatorResult.Error(e)
      }catch (e: SerializationException){
          e.printStackTrace()
          return  return MediatorResult.Error(e)
      }catch (e: Exception){
          e.printStackTrace()
          return return MediatorResult.Error(e)
      }

    }

    override suspend fun initialize(): InitializeAction = withContext(Dispatchers.IO) {
        val hasLocalData = database.characterDao().count() > 0

        return@withContext when {
            !hasLocalData      -> InitializeAction.LAUNCH_INITIAL_REFRESH
            else               -> InitializeAction.SKIP_INITIAL_REFRESH
        }
    }
}