package com.example.rickandmorty.data.database.episode

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.rickandmorty.data.database.character.CharacterEntity

@Dao
interface EpisodeDao {
    @Upsert
    fun upsert(episodes: List<EpisodeEntity>)

    @Query("SELECT * FROM episodeentity")
    fun getAllEpisodes(): PagingSource<Int, EpisodeEntity>

    @Query("DELETE FROM episodeentity")
    fun deleteAllEpisodes()

    @Query("SELECT COUNT(*) FROM episodeentity")
   suspend fun count(): Int
}