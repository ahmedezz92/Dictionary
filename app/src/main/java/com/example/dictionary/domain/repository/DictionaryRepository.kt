package com.example.dictionary.domain.repository

import com.example.dictionary.data.model.DictionaryResponse
import com.example.dictionary.domain.model.BaseResult
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {
    suspend fun getWordDefinitions(
        word: String
    ): Flow<BaseResult<List<DictionaryResponse>>>

    suspend fun getSearchHistoryList(): Flow<List<String>>

    suspend fun saveQueryToHistoryList(query: String)
}