package com.example.dictionaryapplication.presentation.screens.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.data.model.DictionaryResponse
import com.example.dictionary.domain.model.BaseResult
import com.example.dictionary.domain.usecase.GetWordDefinitionUseCase
import com.example.dictionary.domain.utils.NetworkUtils
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
    private val networkUtils: NetworkUtils
) : ViewModel() {

    private val _state =
        MutableStateFlow<GetWordDefinitionState>(GetWordDefinitionState.Init)
    val state: StateFlow<GetWordDefinitionState> =
        _state.asStateFlow()

    private val _isNetworkAvailable = MutableStateFlow(true)
    val isNetworkAvailable: StateFlow<Boolean> = _isNetworkAvailable.asStateFlow()

    private val _isQueriedBefore = MutableStateFlow(false)
    val isQueriedBefore: StateFlow<Boolean> = _isQueriedBefore.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String?> = _searchQuery.asStateFlow()

//    init {
//        _isNetworkAvailable.value = networkUtils.isNetworkAvailable()
//        getCharactersList()
//    }

    fun getCharactersList() {
        if (!_isNetworkAvailable.value) {
            return
        }
        viewModelScope.launch {
            getWordDefinitionUseCase.execute(
                word = _searchQuery.value
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
                            if (result.items!!.isEmpty())
                                _state.value = GetWordDefinitionState.Empty
                            else
                                _state.value = GetWordDefinitionState.Success(result.items.get(0))
                        }
                    }
                }
        }
    }

    fun searchCharacters(query: String?) {
        resetList()
        if (query.isNullOrEmpty()) {
            _searchQuery.value = ""
            _isQueriedBefore.value = false
        } else {
            _searchQuery.value = query
            _isQueriedBefore.value = true
        }
        getCharactersList()
    }

    fun resetList() {
        if (!networkUtils.isNetworkAvailable()) {
            _isNetworkAvailable.value = false
            return
        }
        _isNetworkAvailable.value = true
        _state.value = GetWordDefinitionState.Init
    }
}

sealed class GetWordDefinitionState {
    object Init : GetWordDefinitionState()
    object IsLoading : GetWordDefinitionState()
    data class Success(val data: DictionaryResponse) : GetWordDefinitionState()
    data class Error(val message: String) : GetWordDefinitionState()
    object Empty : GetWordDefinitionState()
}