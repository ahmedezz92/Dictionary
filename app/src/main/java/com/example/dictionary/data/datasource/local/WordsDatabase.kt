package com.example.dictionary.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dictionary.data.datasource.local.entities.DefinitionEntity
import com.example.dictionary.data.datasource.local.entities.SearchHistoryEntity

@Database(entities = [DefinitionEntity::class, SearchHistoryEntity::class], version = 1)
@TypeConverters(DictionaryTypeConverters::class)

abstract class WordsDatabase : RoomDatabase() {
    abstract fun wordsDefinitionDao(): WordsDefinitionDao

    companion object {
        @Volatile
        private var INSTANCE: WordsDatabase? = null

        fun getDatabase(context: Context): WordsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordsDatabase::class.java,
                    "dictionary_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}