package com.example.rickandmorty.data.database.episode

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.rickandmorty.data.database.character.CharacterEntity

@Dao
interface EpisodeDao {
    @Upsert
    fun upsert(characters: List<EpisodeEntity>)

    @Query("SELECT * FROM episodeentity")
    fun getAllCharacters(): PagingSource<Int, EpisodeEntity>

    @Query("DELETE FROM episodeentity")
    fun deleteAllCharacters()
}