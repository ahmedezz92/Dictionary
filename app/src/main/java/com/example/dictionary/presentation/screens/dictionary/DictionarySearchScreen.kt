package com.example.dictionary.presentation.screens.dictionary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.dictionary.R
import com.example.dictionary.presentation.components.SearchBar
import com.example.dictionary.presentation.components.SearchHistoryList
import com.example.dictionary.presentation.components.list.SearchResult
import com.example.dictionary.presentation.components.states.EmptyState
import com.example.dictionary.presentation.components.states.ErrorState
import com.example.dictionary.presentation.components.states.LoadingState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictionarySearchScreen(
    dictionaryViewModel: DictionaryViewModel,
) {
    val currentState by dictionaryViewModel.state.collectAsState()
    val searchHistory by dictionaryViewModel.searchHistory.collectAsState()
    val searchText by dictionaryViewModel.searchText.collectAsState()

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding()
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            onSearch = { word ->
                if (word.isNotEmpty())
                    dictionaryViewModel.searchQuery(word)
                else if (word.isEmpty())
                    dictionaryViewModel.showEmptyState()
            }, modifier = Modifier.padding(),
            searchText = searchText,
            onSearchTextChange = { text ->
                dictionaryViewModel.updateSearchText(text)
            },
            onToggleBottomSheet = { showBottomSheet = true }
        )

        when (val state = currentState) {
            is GetWordDefinitionState.Empty -> EmptyState(error = stringResource(id = R.string.label_empty_result))

            is GetWordDefinitionState.IsLoading ->
                LoadingState()

            is GetWordDefinitionState.Error ->
                ErrorState(error = state.message)


            is GetWordDefinitionState.Success ->
                SearchResult(state.data)


            is GetWordDefinitionState.NoConnection ->
                EmptyState(error = stringResource(id = R.string.label_check_connection))

            else -> {}
        }
    }
    if (showBottomSheet) {
        LaunchedEffect(Unit) {
            dictionaryViewModel.getSearchHistory()
        }
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = bottomSheetState
        ) {
            SearchHistoryList(
                history = searchHistory,
                onWordClick = { word ->
                    dictionaryViewModel.searchQuery(word)
                    showBottomSheet = false
                }
            )
        }
    }
}