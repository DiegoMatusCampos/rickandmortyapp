package com.example.rickandmorty.data.mappers

import com.example.rickandmorty.data.database.character.CharacterEntity
import com.example.rickandmorty.data.database.episode.EpisodeEntity
import com.example.rickandmorty.data.dto.ResultDto
import com.example.rickandmorty.data.dto.ResultEpisodeDto
import com.example.rickandmorty.domain.model.Character


private fun String.toEpisodeId(): Int =
    substringAfterLast('/').toInt()


fun parseEpisodeCode(code: String): Pair<Int, Int>? {
    val regex = Regex("""S(\d{2})E(\d{2})""", RegexOption.IGNORE_CASE)
    val matchResult = regex.find(code)

    return matchResult?.let {
        val season = it.groupValues[1].toInt()
        val episode = it.groupValues[2].toInt()
        season to episode
    }
}


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

fun ResultEpisodeDto.toEpisodeEntity(): EpisodeEntity {

    val (seasonNumber, episodeNumber) = parseEpisodeCode(this.episode)!!

    return EpisodeEntity(
        id = this.id,
        name = this.name,
        airDate = this.airDate,
        episodeNumber = episodeNumber,
        seasonNumber = seasonNumber
    )
}