package com.example.dictionary.di

import com.example.dictionary.data.core.data.module.NetworkModule
import com.example.dictionary.data.datasource.local.WordsDefinitionDao
import com.example.dictionary.data.datasource.remote.DictionaryApi
import com.example.dictionary.data.repository.DictionaryRepositoryImpl
import com.example.dictionary.domain.repository.DictionaryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): DictionaryApi {
        return retrofit.create(DictionaryApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(
        dictionaryApi: DictionaryApi,
        dao: WordsDefinitionDao
    ): DictionaryRepository {
        return DictionaryRepositoryImpl(dictionaryApi, dao)
    }
}