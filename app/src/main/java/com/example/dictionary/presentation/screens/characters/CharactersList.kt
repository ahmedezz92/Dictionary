package com.example.dictionary.presentation.screens.characters

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.dictionary.R
import com.example.dictionary.presentation.components.AnimatedSearchBar
import com.example.dictionaryapplication.presentation.components.connection.NoInternetConnection
import com.example.dictionaryapplication.presentation.components.states.EmptyState
import com.example.dictionaryapplication.presentation.components.states.ErrorState
import com.example.dictionaryapplication.presentation.components.states.LoadingState
import com.example.dictionaryapplication.presentation.screens.characters.DictionaryViewModel
import com.example.dictionaryapplication.presentation.screens.characters.GetWordDefinitionState


@Composable
fun CharactersList(dictionaryViewModel: DictionaryViewModel, navController: NavHostController) {

    val searchQuery by dictionaryViewModel.searchQuery.collectAsState()
    val currentState by dictionaryViewModel.state.collectAsState()
    val isNetworkAvailable by dictionaryViewModel.isNetworkAvailable.collectAsState()
    val isQueriedBefore by dictionaryViewModel.isQueriedBefore.collectAsState()

    if (!isNetworkAvailable) {
        NoInternetConnection(onRetry = {
            dictionaryViewModel.getCharactersList()
        })
    } else {
        Column(
            modifier = Modifier
                .padding()
                .fillMaxSize()
                .background(color = colorResource(id = R.color.teal_700)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedSearchBar(
                onSearch = { word, isCancel ->
                    if (word != searchQuery && !isCancel)
                        dictionaryViewModel.searchCharacters(word)
                    else if (isCancel && isQueriedBefore)
                        dictionaryViewModel.searchCharacters(null)

                }, modifier = Modifier.padding()

            )
            when (val state = currentState) {
                is GetWordDefinitionState.Empty -> {
                    EmptyState(empty = stringResource(id = R.string.label_empty_result))
                }

                is GetWordDefinitionState.IsLoading -> {
                    LoadingState()
                }

                is GetWordDefinitionState.Error -> {
                    ErrorState(error = state.message)
                }

                is GetWordDefinitionState.Success -> {
                    Log.d("word", state.data.word)
                }

                else -> {}
            }
        }
    }
}