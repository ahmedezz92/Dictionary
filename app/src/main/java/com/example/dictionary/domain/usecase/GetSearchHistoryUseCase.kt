package com.example.dictionary.domain.usecase

import com.example.dictionary.data.model.DictionaryResponse
import com.example.dictionary.domain.model.BaseResult
import com.example.dictionary.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchHistoryUseCase @Inject constructor(private val dictionaryRepository: DictionaryRepository) {
    suspend fun execute(): Flow<List<String>> {
        return dictionaryRepository.getSearchHistoryList()
    }
}