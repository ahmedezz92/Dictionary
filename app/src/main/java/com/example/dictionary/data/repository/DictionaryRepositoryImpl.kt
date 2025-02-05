package com.example.dictionary.data.repository

import com.example.dictionary.data.core.data.utils.WrappedErrorResponse
import com.example.dictionary.data.datasource.local.WordsDefinitionDao
import com.example.dictionary.data.datasource.remote.DictionaryApi
import com.example.dictionary.data.model.DictionaryResponse
import com.example.dictionary.domain.model.BaseResult
import com.example.dictionary.domain.repository.DictionaryRepository
import com.example.dictionary.toDefinitionEntity
import com.example.dictionary.toSearchEntity
import com.example.dictionary.toWordDefinition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val api: DictionaryApi, private val wordsDefinitionDao: WordsDefinitionDao
) : DictionaryRepository {
    override suspend fun getWordDefinitions(word: String): Flow<BaseResult<List<DictionaryResponse>>> {
        return flow {
            try {
                val response = api.getWordDefinitions(
                    word = word
                )
                if (response.isSuccessful) {
                    val body = response.body()!!
                    val wordDefinition = body[0].toDefinitionEntity(word)
                    wordsDefinitionDao.insertWordDefinition(wordDefinition)
                    emit(BaseResult.DataState(body))
                } else {
                    val errorBody = response.errorBody()?.charStream()
                    val type = object : TypeToken<WrappedErrorResponse>() {}.type
                    val errorResponse: WrappedErrorResponse = Gson().fromJson(errorBody, type)
                    emit(BaseResult.ErrorState(errorResponse))
                }
            } catch (e: Exception) {
                val cachedDefinitions = wordsDefinitionDao.getWordDefinition(word)
                if (cachedDefinitions != null) {
                    emit(BaseResult.DataState(cachedDefinitions.toWordDefinition()))
                } else {
                    emit(
                        BaseResult.ErrorState(
                            errorResponse = WrappedErrorResponse(
                                "404", "Please, Check your internet connection"
                            )
                        )
                    )
                }
            }
        }
    }

    override suspend fun getSearchHistoryList(): Flow<List<String>> {
        return flow {
            emit(wordsDefinitionDao.getSearchHistoryList())
        }
    }

    override suspend fun saveQueryToHistoryList(query: String) {
        wordsDefinitionDao.insertQuery(toSearchEntity(query))
    }
}
