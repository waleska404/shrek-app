package com.waleska404.shrek.data.local

import androidx.room.TypeConverter

class DatabaseConverter {

    private val separator = ","

    @TypeConverter
    fun convertListToString(list: List<String>): String {
        return list.joinToString(separator = separator)
    }

    @TypeConverter
    fun convertStringToList(string: String): List<String> {
        return string.split(separator)
    }
}