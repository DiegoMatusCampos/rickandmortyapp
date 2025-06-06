package com.example.rickandmorty.data.database.character

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.rickandmorty.data.database.character.CharacterEntity

@Dao
interface CharacterDao {

    @Upsert
    fun upsert(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characterentity ")
    fun getAllCharacters(): PagingSource<Int, CharacterEntity>

    @Query("SELECT * FROM characterentity WHERE id= :id")
    suspend fun getCharacterById(id:Int): CharacterEntity?


    @Query("DELETE FROM characterentity")
    fun deleteAllCharacters()

    @Query("SELECT COUNT(*) FROM characterentity")
    suspend fun count(): Int
}