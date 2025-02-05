package com.example.dictionary.data.datasource.local

import androidx.room.TypeConverter
import com.example.dictionary.data.model.Meaning
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DictionaryTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromMeaningsList(meanings: List<Meaning>): String {
        return gson.toJson(meanings)
    }

    @TypeConverter
    fun toMeaningsList(meaningsString: String): List<Meaning> {
        val listType = object : TypeToken<List<Meaning>>() {}.type
        return gson.fromJson(meaningsString, listType)
    }

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toStringList(string: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(string, listType)
    }

}