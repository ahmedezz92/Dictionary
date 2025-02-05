package com.example.dictionary

import com.example.dictionary.data.datasource.local.WordsDefinitionDao
import com.example.dictionary.data.datasource.remote.DictionaryApi
import com.example.dictionary.data.model.DictionaryResponse
import com.example.dictionary.data.repository.DictionaryRepositoryImpl
import com.example.dictionary.domain.model.BaseResult
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.Response

class DictionaryRepositoryImplTest {

    @Mock
    private lateinit var api: DictionaryApi

    @Mock
    private lateinit var dao: WordsDefinitionDao

    private lateinit var repository: DictionaryRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = DictionaryRepositoryImpl(api, dao)
    }

    @Test
    fun `getWordDefinitions should return success state when API call is successful`() =
        runBlocking {
            // Arrange
            val word = "test"
            val mockResponse = DictionaryResponse(
                word = "test",
                phonetic = "test",
                meanings = emptyList(),
            )
            val mockResponseList = listOf(mockResponse)
            val apiResponse = Response.success(listOf(mockResponse))

            whenever(api.getWordDefinitions(word)).thenReturn(apiResponse)

            // Act
            val result = repository.getWordDefinitions(word).toList()

            // Assert
            assertTrue(result[0] is BaseResult.DataState)
            assertEquals(
                mockResponseList,
                (result[0] as BaseResult.DataState<List<DictionaryResponse>>).items
            )

        }

    @Test
    fun `getWordDefinitions should return error state when API call fails and no cache exists`() =
        runBlocking {
            val word = "test"
            whenever(api.getWordDefinitions(word)).thenThrow(RuntimeException())
            whenever(dao.getWordDefinition(word)).thenReturn(null)

            // Act
            val result = repository.getWordDefinitions(word).toList()

            // Assert
            assertTrue(result[0] is BaseResult.ErrorState)
            assertEquals("404", (result[0] as BaseResult.ErrorState).errorResponse.code)
            assertEquals(
                "Please, Check your internet connection",
                (result[0] as BaseResult.ErrorState).errorResponse.message
            )
        }
}