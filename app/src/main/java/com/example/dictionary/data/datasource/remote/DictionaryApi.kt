package com.example.dictionary.data.datasource.remote

import com.example.dictionary.data.model.DictionaryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("entries/en/{word}")
    suspend fun getWordDefinitions(
        @Path("word") word: String
    ): Response<List<DictionaryResponse>>
}