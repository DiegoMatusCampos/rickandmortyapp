package com.example.rickandmorty.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TypeConverts {
    @TypeConverter
    fun fromIntListJson(list: List<Int>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun toIntListJson(json: String): List<Int> {
        return Json.decodeFromString(json)
    }
}