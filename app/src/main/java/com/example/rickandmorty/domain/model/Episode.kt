package com.example.rickandmorty.domain.model

data class Episode(
    val id: Int,
    val name: String,
    val airDate: String,
    val episodeNumber: Int,
    val seasonNumber: Int
)
