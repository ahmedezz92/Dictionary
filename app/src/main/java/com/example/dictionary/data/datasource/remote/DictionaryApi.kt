package com.example.dictionary.data.datasource.remote

import com.example.dictionary.data.core.data.utils.WrappedResponse
import com.example.dictionary.data.model.DictionaryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface DictionaryApi {
    @GET("characters")
    suspend fun getCharacters(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("name") name: String?,
        @Query("apikey") apiKey: String,
        @Query("ts") timestamp: String,
        @Query("hash") hash: String
    ): Response<WrappedResponse<DictionaryResponse>>

    @GET
    suspend fun getResourceImage(
        @Url url: String,
        @Query("apikey") apiKey: String,
        @Query("ts") timestamp: String,
        @Query("hash") hash: String
    ): Response<WrappedResponse<DictionaryResponse>>

    @GET("entries/en/{word}")
    suspend fun getWordDefinitions(
        @Path("word") word: String
    ): Response<List<DictionaryResponse>>

}