package com.example.probuilder.presentation.screen.categories.create_service

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.R
import com.example.probuilder.common.Constants
import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.SearchItem
import com.example.probuilder.presentation.components.PrimaryButton
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.components.TextFieldWithTitle
import com.example.probuilder.presentation.screen.categories.categories_screen.component.DropDownSearch
import com.example.probuilder.presentation.screen.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateServiceScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateServiceViewModel = hiltViewModel(),
    bottomBar: @Composable() (() -> Unit),
    onBack: () -> Unit
) {
    Scaffold(
        bottomBar = bottomBar,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Main manu"
                        )
                    }
                },
                title = { Text(text = stringResource(R.string.CATEGORY_PAGE_TITLE)) },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = "Search"
                        )
                    }
                },
            )
        }
    ) {
        Column(
            modifier.padding(it).padding(Constants.HORIZONTAL_PADDING),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        ) {
            val newService by viewModel.newService
            val screenState by viewModel.screenState.collectAsState()
            val categories by viewModel.allCategories.collectAsState(emptyList())
            val selectedCategory by viewModel.selectedCategory
            TextFieldWithTitle(
                title = "Назва послуги",
                value = newService.name,
                onValueChange = { viewModel.onEvent(CreateServiceEvent.SetServiceName(it)) }
            )
            DropDownSearch(
                searchTitle = "Категорія",
                currentItem = SearchItem(selectedCategory.name, selectedCategory),
                searchItems = categories.map { SearchItem(it.name, it) },
                selectItem = { searchItem -> viewModel.onEvent(CreateServiceEvent
                    .UpdateCurrentCategory(searchItem.item as Category))
                }
            )
            TextFieldWithTitle(
                title = "Одиниця виміру",
                value = newService.measureUnit,
                onValueChange = { viewModel.onEvent(CreateServiceEvent.SetMeasureUnit(it)) }
            )
            TextFieldWithTitle(
                title = "Ціна за одиницю",
                value = screenState.pricePerUnit,
                onValueChange = { viewModel.onEvent(CreateServiceEvent.SetPricePerUnit(it)) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            PrimaryButton(onClick = {
                viewModel.onEvent(CreateServiceEvent.SaveService)
                onBack()
            }) {
                Text(text = "Зберегти", style = Typography.labelLarge)
            }
            SecondaryButton(onClick = onBack) {
                Text(text = "Відмінити", style = Typography.labelLarge)
            }
        }
    }
}
