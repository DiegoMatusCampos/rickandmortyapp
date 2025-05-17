package com.example.rickandmorty.data.mappers

import com.example.rickandmorty.data.database.CharacterEntity
import com.example.rickandmorty.data.dto.ResultDto
import com.example.rickandmorty.domain.model.Character

fun ResultDto.toCharacter() = Character(
    name = this.name,
    specie = this.species,
    status = this.status,
    image = this.image,
    gender = this.gender,
    origin = this.origin.name,
    lastLocation = this.location.name,
    episodes = this.episode.map { it.last().toInt() },
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
    episodes = this.episode.map { it.last().toInt() }
)

fun CharacterEntity.toCharacter() = Character(
    name = this.name,
    specie = this.specie,
    status = this.status,
    gender = this.gender,
    origin = this.origin,
    lastLocation = this.lastLocation,
    episodes = this.episodes,
    image = this.image
)