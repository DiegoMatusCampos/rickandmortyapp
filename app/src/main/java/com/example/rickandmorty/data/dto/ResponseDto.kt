package com.example.rickandmorty.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class  ResponseDto<T>(
    val info: InfoDto,
    val results: List<T>
)