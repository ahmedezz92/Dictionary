package com.example.dictionary

import com.example.dictionary.data.datasource.local.entities.DefinitionEntity
import com.example.dictionary.data.datasource.local.entities.SearchHistoryEntity
import com.example.dictionary.data.model.DictionaryResponse

fun DictionaryResponse.toDefinitionEntity(word: String): DefinitionEntity {
    return DefinitionEntity(
        wordDefinition = word,
        word = this.word,
        phonetic = this.phonetic,
        meaning = this.meanings
    )
}

fun DefinitionEntity.toWordDefinition(): List<DictionaryResponse> {
    return listOf(
        DictionaryResponse(
            word = this.word,
            phonetic = this.phonetic,
            meanings = this.meaning
        )
    )
}

fun toSearchEntity(word: String): SearchHistoryEntity {
    return SearchHistoryEntity(
        searchQuery = word
    )
}