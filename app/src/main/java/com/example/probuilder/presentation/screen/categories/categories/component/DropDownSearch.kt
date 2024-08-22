package com.example.probuilder.presentation.screen.categories.categories.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.probuilder.domain.model.SearchItem
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun DropDownSearch(
    modifier: Modifier = Modifier,
    searchTitle: String = "",
    currentItem: SearchItem,
    selectItem: (SearchItem) -> Unit,
    searchItems: List<SearchItem>,
) {
    var isSearchExpanded by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf(TextFieldValue(currentItem.name)) }
    val searchFieldFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopEnd)
            .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }
    ) {
        Column {
            if (searchTitle.isNotEmpty()) Text(
                modifier = Modifier.padding(bottom = 5.dp),
                text = searchTitle,
                style = Typography.titleSmall
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(searchFieldFocusRequester)
                    .onFocusChanged { focusState -> isSearchExpanded = focusState.isFocused },
                value = inputText,
                onValueChange = {
                    inputText = it
                    isSearchExpanded = true
                },
                shape = RoundedCornerShape(8.dp),
                trailingIcon = {
                    Icon(
                        modifier = modifier.clickable {
                            inputText = TextFieldValue("")
                            searchFieldFocusRequester.requestFocus()
                        },
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null
                    )
                }
            )

            if (isSearchExpanded) {
                SearchSuggestions(
                    searchItems = searchItems,
                    currentItem = currentItem,
                    inputText = inputText.text,
                    selectItem = {
                        selectItem(it)
                        inputText = TextFieldValue(it.name, TextRange(it.name.length))
                        isSearchExpanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun SearchSuggestions(
    searchItems: List<SearchItem>,
    currentItem: SearchItem,
    inputText: String,
    selectItem: (SearchItem) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val filteredItems = searchItems.filter {
        it != currentItem && it.name.contains(inputText, ignoreCase = true) }

    LazyColumn(
        modifier = Modifier.pointerInput(Unit) {
            detectVerticalDragGestures { change, dragAmount -> keyboardController?.hide() }
        }
    ) {
        items(filteredItems) { searchItem ->
            Button(onClick = {
                selectItem(searchItem)
            }) {
                Text(text = searchItem.name)
            }
        }
    }
}
