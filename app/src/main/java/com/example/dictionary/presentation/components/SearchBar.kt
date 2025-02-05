package com.example.dictionary.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.dictionary.R

@Composable
fun SearchBar(
    searchText: String,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    showSearchHistoryList: (Boolean) -> Unit,
    onSearchTextChange: (String) -> Unit,
) {
    var word by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }
    var isHistoryShowing by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Gray),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                word = it
                onSearchTextChange(it)
            },
            modifier = Modifier
                .weight(1f)
                .padding(10.dp)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.white),
                unfocusedBorderColor = colorResource(id = R.color.white),
                cursorColor = Color.Black
            ),
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.hint_enter_name),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White
                    )
                )
            },
            textStyle = TextStyle(Color.White),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(searchText)
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    isHistoryShowing = false
                    showSearchHistoryList(false)
                }
            ),
            leadingIcon = {
                IconButton(
                    onClick = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        isHistoryShowing = !isHistoryShowing
                        if (isHistoryShowing) {
                            showSearchHistoryList(true)
                        } else {
                            showSearchHistoryList(false)
                        }
                    },
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "History item",
                        tint = Color.White,
                    )
                }
            }
        )
        IconButton(
            onClick = {
                onSearch(searchText)
                keyboardController?.hide()
                focusManager.clearFocus()
                isHistoryShowing = false
                showSearchHistoryList(false)
            },
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = Color.Gray,
                    shape = CircleShape
                )
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_search),
                contentDescription = "Search",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun SearchHistoryList(
    history: List<String>,
    onWordClick: (String) -> Unit
) {
    LazyColumn {
        items(history) { word ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onWordClick(word)
                    }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = word)
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "History item"
                )
            }
        }
    }
}