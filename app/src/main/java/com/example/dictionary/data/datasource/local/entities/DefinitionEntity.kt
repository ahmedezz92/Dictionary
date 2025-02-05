package com.example.dictionary.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionary.data.model.Meaning

@Entity(tableName = "words_table")
data class DefinitionEntity(
    @PrimaryKey
    val wordDefinition: String,
    val word: String,
    val phonetic: String?,
    val meaning: List<Meaning>
)