package com.example.dictionary.presentation.screens.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dictionary.R
import com.example.dictionary.presentation.screens.dictionary.DictionarySearchScreen
import com.example.dictionary.presentation.screens.dictionary.DictionaryViewModel

@Composable
fun DictionaryApp(
    dictionaryViewModel: DictionaryViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    Scaffold(
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "CharacterList",
            modifier = Modifier
                .padding(padding)
                .background(color = colorResource(id = R.color.black))
        ) {
            composable("CharacterList") {
                DictionarySearchScreen(
                    dictionaryViewModel = dictionaryViewModel,
                )
            }
        }
    }
}