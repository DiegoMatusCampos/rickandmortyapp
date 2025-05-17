package com.example.rickandmorty.domain.model

data class Character(
    val name: String,
    val specie: String,
    val status: String,
    val gender: String,
    val origin: String,
    val lastLocation: String,
    val episodes: List<Int>,
    val image: String
)
