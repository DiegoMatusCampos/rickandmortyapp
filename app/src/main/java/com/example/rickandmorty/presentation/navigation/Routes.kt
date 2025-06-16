package com.example.rickandmorty.presentation.navigation
import com.example.rickandmorty.domain.model.Character
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
object CharacterList

@Serializable
data class CharacterDetail(val characterId: Int)

@Serializable
data class EpisodeList(val episodesIds: List<Int>)