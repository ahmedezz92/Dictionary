package com.example.dictionary.presentation.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dictionary.data.model.DictionaryResponse
import com.example.dictionary.data.model.Meaning

@Composable
fun WordDefinitionItem(
    definition: DictionaryResponse
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = definition.word,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                definition.phonetic?.let {
                    Text(
                        text = it,
                        fontSize = 18.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        definition.meanings.forEach { meaning ->
            MeaningSection(meaning)
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun MeaningSection(meaning: Meaning) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = meaning.partOfSpeech,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(16.dp))
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = Color.LightGray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        meaning.definitions.forEach { definition ->
            Row(modifier = Modifier.padding(bottom = 16.dp)) {
                Text(
                    text = "•",
                    modifier = Modifier.padding(end = 8.dp),
                    color = Color.Black
                )
                Column {
                    Text(
                        text = definition.definition,
                        color = Color.Black
                    )
                    Text(
                        text = "\"${definition.example}\"",
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp, start = 16.dp)
                    )
                }
            }
        }
    }
}