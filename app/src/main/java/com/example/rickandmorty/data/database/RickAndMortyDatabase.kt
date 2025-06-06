package com.example.rickandmorty.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmorty.data.database.character.CharacterDao
import com.example.rickandmorty.data.database.character.CharacterEntity
import com.example.rickandmorty.data.database.episode.EpisodeDao
import com.example.rickandmorty.data.database.episode.EpisodeEntity

@Database(
    entities = [
        CharacterEntity::class,
        EpisodeEntity::class
    ],
    version = 2
)
@TypeConverters(Converters::class)
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun episodeDao(): EpisodeDao
}