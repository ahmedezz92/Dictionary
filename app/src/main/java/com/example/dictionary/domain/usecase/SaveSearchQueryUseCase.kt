package com.example.dictionary.domain.usecase

import com.example.dictionary.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveSearchQueryUseCase @Inject constructor(private val dictionaryRepository: DictionaryRepository) {
    suspend fun execute(query: String) {
        return dictionaryRepository.saveQueryToHistoryList(query = query)
    }
}