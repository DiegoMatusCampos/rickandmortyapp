package com.example.rickandmorty.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromIntListJson(list: List<Int>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun toIntListJson(json: String): List<Int> {
        return Json.decodeFromString(json)
    }
}