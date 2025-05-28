package com.example.rickandmorty.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: Int,
    val name: String,
    val specie: String,
    val status: String,
    val gender: String,
    val origin: String,
    val lastLocation: String,
    val episodes: List<Int>,
    val image: String
)
