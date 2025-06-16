package com.example.rickandmorty.presentation.util

import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.model.Episode

object DataFake {
        val fakeCharacterItems = listOf<Character>(
            Character(
                name = "Rick",
                specie = "Human",
                status = "Alive",
                gender = "Male",
                origin = "Eart",
                lastLocation = "Desconocida",
                episodes = listOf(1,2,3,4,5,6,7),
                image = "imagen",
                id = 1
            ),
            Character(
                name = "Morty",
                specie = "Human",
                status = "Alive",
                gender = "Male",
                origin = "Eart",
                lastLocation = "Desconocida",
                episodes = listOf(1,2,3,4,5,6,7),
                image = "imagen",
                id = 2
            )
        )
    
    val fakeEpisodesItems = listOf<Episode>(
        Episode(
            id = 1,
            name = "Pilot",
            airDate = "December 2, 2013",
            episodeNumber = 1,
            seasonNumber = 1
        ),
        Episode(
            id = 2,
            name = "Lawnmower Dog",
            airDate = "December 9, 2013",
            episodeNumber = 2,
            seasonNumber = 1
        ),
        Episode(
            id = 13,
            name = "Mortynight Run",
            airDate = "August 2, 2015",
            episodeNumber = 2,
            seasonNumber = 2
        )
    )

}