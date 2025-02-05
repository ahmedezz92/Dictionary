package com.example.dictionary.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_list_table")
data class SearchHistoryEntity (
    @PrimaryKey
    val searchQuery: String,
)