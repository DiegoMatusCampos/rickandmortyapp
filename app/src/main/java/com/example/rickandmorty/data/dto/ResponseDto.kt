package com.example.rickandmorty.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto(
    val info: InfoDto,
    val results: List<ResultDto>
)