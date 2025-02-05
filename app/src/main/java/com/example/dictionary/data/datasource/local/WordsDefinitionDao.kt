package com.example.dictionary.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionary.data.datasource.local.entities.DefinitionEntity
import com.example.dictionary.data.datasource.local.entities.SearchHistoryEntity

@Dao
interface WordsDefinitionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordDefinition(definitionEntity: DefinitionEntity)

    @Query("SELECT * FROM words_table WHERE wordDefinition = :wordDefinition")
    suspend fun getWordDefinition(wordDefinition: String): DefinitionEntity?

    @Query("SELECT * FROM search_list_table")
    suspend fun getSearchHistoryList(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuery(entity: SearchHistoryEntity)

}