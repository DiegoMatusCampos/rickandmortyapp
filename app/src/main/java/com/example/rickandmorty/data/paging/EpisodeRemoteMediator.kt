package com.example.rickandmorty.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.rickandmorty.data.database.RickAndMortyDatabase
import com.example.rickandmorty.data.database.episode.EpisodeEntity
import com.example.rickandmorty.data.network.ApiService
import javax.inject.Inject

@ExperimentalPagingApi
class EpisodeRemoteMediator @Inject constructor(
    private val database: RickAndMortyDatabase,
    private val api: ApiService
): RemoteMediator<Int, EpisodeEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EpisodeEntity>
    ): MediatorResult {
        val page = when(loadType){
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                if (lastItem == null) return MediatorResult.Success(true)
                (lastItem.id/state.config.pageSize) + 1
            }
        }


         return MediatorResult.Success(endOfPaginationReached = true)


    }
}