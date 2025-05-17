package com.example.rickandmorty.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    val name: String,
    val specie: String,
    val status: String,
    val gender: String,
    val origin: String,
    val lastLocation: String,
    val episodes: List<Int>,
    val image: String,
    @PrimaryKey val id: Int
)