package com.example.dictionary.domain.repository

import com.example.dictionary.data.core.data.utils.WrappedResponse
import com.example.dictionary.data.model.DictionaryResponse
import com.example.dictionary.domain.model.BaseResult
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {
    fun getCharactersList(
        offset: Int,
        limit: Int,
        name: String?
    ): Flow<BaseResult<WrappedResponse<DictionaryResponse>>>

    fun getMediaImages(
        imageURI: String,
    ): Flow<BaseResult<WrappedResponse<DictionaryResponse>>>

    suspend fun getWordDefinitions(
        word: String
    ): Flow<BaseResult<List<DictionaryResponse>>>
}