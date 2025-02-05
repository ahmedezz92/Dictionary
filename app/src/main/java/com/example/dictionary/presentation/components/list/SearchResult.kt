package com.example.dictionary.presentation.components.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dictionary.data.model.DictionaryResponse

@Composable
fun SearchResult(definition: DictionaryResponse) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
    {
        item {
            WordDefinitionItem(definition = definition)
        }
    }
}