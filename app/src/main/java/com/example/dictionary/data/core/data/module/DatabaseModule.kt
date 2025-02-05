package com.example.dictionary.data.core.data.module

import android.content.Context
import com.example.dictionary.data.datasource.local.WordsDatabase
import com.example.dictionary.data.datasource.local.WordsDefinitionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WordsDatabase {
        return WordsDatabase.getDatabase(context)
    }

    @Provides
    fun provideDao(database: WordsDatabase): WordsDefinitionDao {
        return database.wordsDefinitionDao()
    }
}