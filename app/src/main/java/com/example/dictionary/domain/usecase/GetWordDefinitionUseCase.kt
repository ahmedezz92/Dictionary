package com.example.dictionary.domain.usecase

import com.example.dictionary.data.model.DictionaryResponse
import com.example.dictionary.domain.model.BaseResult
import com.example.dictionary.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWordDefinitionUseCase @Inject constructor(private val dictionaryRepository: DictionaryRepository) {
    suspend fun execute(
        word: String
    ): Flow<BaseResult<List<DictionaryResponse>>> {
        return dictionaryRepository.getWordDefinitions(word)
    }
}