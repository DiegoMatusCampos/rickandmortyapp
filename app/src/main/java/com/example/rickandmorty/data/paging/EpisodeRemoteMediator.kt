package com.example.rickandmorty.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickandmorty.data.database.RickAndMortyDatabase
import com.example.rickandmorty.data.database.episode.EpisodeEntity
import com.example.rickandmorty.data.mappers.toEpisodeEntity
import com.example.rickandmorty.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import java.nio.channels.UnresolvedAddressException
import javax.inject.Inject

@ExperimentalPagingApi
class EpisodeRemoteMediator @Inject constructor(
    private val database: RickAndMortyDatabase,
    private val api: ApiService,
    private val ids: String,
) : RemoteMediator<Int, EpisodeEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EpisodeEntity>
    ): MediatorResult {

        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) return MediatorResult.Success(true)
                    (lastItem.id / state.config.pageSize) + 1
                }
            }

            val response = api.getEpisodes(ids)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.episodeDao().deleteAllEpisodes()
                }

                database.episodeDao()
                    .upsert(response.body()?.results?.map { it.toEpisodeEntity() } ?: listOf())
            }

            return MediatorResult.Success(
                endOfPaginationReached = response.body()?.results?.isEmpty() ?: true
            )
        } catch (e: UnresolvedAddressException) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        } catch (e: SerializationException) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        } catch (e: Exception) {
            e.printStackTrace()
            return return MediatorResult.Error(e)
        }


    }

    override suspend fun initialize(): InitializeAction = withContext(Dispatchers.IO) {
        val hasLocalData = database.episodeDao().count() > 0

        return@withContext when {
            !hasLocalData      -> InitializeAction.LAUNCH_INITIAL_REFRESH
            else               -> InitializeAction.SKIP_INITIAL_REFRESH
        }
    }
}