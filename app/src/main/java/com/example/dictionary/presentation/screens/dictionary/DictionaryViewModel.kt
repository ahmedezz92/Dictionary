package com.example.dictionary.presentation.screens.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.data.model.DictionaryResponse
import com.example.dictionary.domain.model.BaseResult
import com.example.dictionary.domain.usecase.GetSearchHistoryUseCase
import com.example.dictionary.domain.usecase.GetWordDefinitionUseCase
import com.example.dictionary.domain.usecase.SaveSearchQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val getWordDefinitionUseCase: GetWordDefinitionUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val saveSearchQueryUseCase: SaveSearchQueryUseCase
) : ViewModel() {
    private val _state =
        MutableStateFlow<GetWordDefinitionState>(GetWordDefinitionState.Init)
    val state: StateFlow<GetWordDefinitionState> =
        _state.asStateFlow()

    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory: StateFlow<List<String>> = _searchHistory.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private var lastSearchedQuery: String? = null

    private fun getDefinition(query: String) {
        viewModelScope.launch {
            saveSearchQueryUseCase.execute(query)
            getWordDefinitionUseCase.execute(
                word = query
            )
                .onStart {
                    _state.value =
                        GetWordDefinitionState.IsLoading
                }
                .catch {
                    _state.value = GetWordDefinitionState.Error(it.message!!)
                }
                .collect { result ->
                    when (result) {
                        is BaseResult.ErrorState -> {
                            _state.value =
                                GetWordDefinitionState.Error(result.errorResponse.message)
                        }

                        is BaseResult.DataState -> {
                            lastSearchedQuery = query
                            _searchText.value = query
                            if (result.items!!.isEmpty())
                                _state.value = GetWordDefinitionState.Empty
                            else
                                _state.value = GetWordDefinitionState.Success(result.items[0])
                        }
                    }
                }
        }
    }

    fun getSearchHistory() {
        viewModelScope.launch {
            getSearchHistoryUseCase.execute()
                .collect { history ->
                    _searchHistory.value = history
                }
        }
    }

    fun searchQuery(query: String?) {
        if (!query.isNullOrEmpty() && query != lastSearchedQuery) {
            getDefinition(query)
            updateSearchText(query)
        }
    }

    fun showEmptyState() {
        _state.value = GetWordDefinitionState.Empty
        updateSearchText("")

    }

    fun updateSearchText(text: String) {
        _searchText.value = text
    }
}

sealed class GetWordDefinitionState {
    object Init : GetWordDefinitionState()
    object IsLoading : GetWordDefinitionState()
    data class Success(val data: DictionaryResponse) : GetWordDefinitionState()
    data class Error(val message: String) : GetWordDefinitionState()
    object Empty : GetWordDefinitionState()
    object NoConnection : GetWordDefinitionState()
}