package com.example.rickandmorty.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Upsert
    fun upsert(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characterentity ")
    fun getAllCharacters(): PagingSource<Int, CharacterEntity>

    @Query("SELECT * FROM characterentity WHERE id= :id")
    fun getCharacterById(id:Int): CharacterEntity


    @Query("DELETE FROM characterentity")
    fun deleteAllCharacters()
}