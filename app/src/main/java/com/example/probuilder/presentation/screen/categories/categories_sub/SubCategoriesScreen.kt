package com.example.probuilder.presentation.screen.categories.categories_sub

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.common.Constants.HORIZONTAL_PADDING
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.screen.categories.categories_screen.CreateCategoryOverlay
import com.example.probuilder.presentation.screen.categories.component.CategoryListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubCategoryScreen(
    modifier: Modifier = Modifier,
    nextScreen: (String) -> Unit,
    viewModel: SubCategoriesViewModel = hiltViewModel(),
    bottomBar: @Composable() (() -> Unit)
) {

    val subCategories by viewModel.curSubcategories.observeAsState()
    var isEditMode by rememberSaveable { mutableStateOf(false) }
    if (subCategories.isNullOrEmpty()) {
        viewModel.loadSubCategories()
    }
    Scaffold(
        bottomBar = bottomBar,
        floatingActionButton = {
            FloatingActionButton(
                modifier = modifier.padding(16.dp),
                containerColor = Color(0xFFF2F2F2),
                contentColor = MaterialTheme.colorScheme.primary,
                onClick = { isEditMode = true },
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },
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
                title = { Text(text = "Категорії") },
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
        Box(modifier = modifier
            .padding(it)
            .padding(HORIZONTAL_PADDING)
            .fillMaxSize()) {
            LazyColumn {
                items(subCategories!!) { subCategory ->
                    CategoryListItem(
                        text = subCategory.name,
                        onClick = {
                            nextScreen(
                                Route.SERVICES
                                    .replace("{categoryId}", subCategory.id)
                                    .replace("{subCategoryName}", subCategory.name)

                                    .replace("{categoryName}", viewModel.curCategoryName)
                            )
                        },
                        handleSelect = {},
                    )
                    HorizontalDivider(color = Color.Gray)
                }
            }

            if (isEditMode) {
                CreateCategoryOverlay(
                    onCancel = { isEditMode = false },
                    onSave = { isEditMode = false }
                )
            }
        }
    }
}
