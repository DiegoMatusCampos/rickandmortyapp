package com.example.rickandmorty.data.mappers

import com.example.rickandmorty.data.database.character.CharacterEntity
import com.example.rickandmorty.data.dto.ResultDto
import com.example.rickandmorty.domain.model.Character


private fun String.toEpisodeId(): Int =
    substringAfterLast('/').toInt()


fun ResultDto.toCharacter() = Character(
    id = this.id,
    name = this.name,
    specie = this.species,
    status = this.status,
    image = this.image,
    gender = this.gender,
    origin = this.origin.name,
    lastLocation = this.location.name,
    episodes = this.episode.map { it.toEpisodeId() },
)

fun ResultDto.toCharacterEntity() = CharacterEntity(
    name = this.name,
    specie = this.species,
    status = this.status,
    image = this.image,
    id = this.id,
    gender = this.gender,
    origin = this.origin.name,
    lastLocation = this.location.name,
    episodes = this.episode.map { it.toEpisodeId() }
)

fun CharacterEntity.toCharacter() = Character(
    id = this.id,
    name = this.name,
    specie = this.specie,
    status = this.status,
    gender = this.gender,
    origin = this.origin,
    lastLocation = this.lastLocation,
    episodes = this.episodes,
    image = this.image
)