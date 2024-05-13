package com.example.probuilder.presentation.screen.categories.categories_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.components.CustomFloatingButton
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoriesScreenStep.CATEGORIES_SCREEN
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoriesScreenStep.CREATE_CATEGORY_OVERLAY
import com.example.probuilder.presentation.screen.categories.categories_screen.drop_down_edit_menu.DropDownEditMenu
import com.example.probuilder.presentation.screen.categories.categories_screen.overflow_menu.CategoryOverflowMenu
import com.example.probuilder.presentation.screen.categories.categories_screen.services_section.ServicesSection
import com.example.probuilder.presentation.screen.categories.component.CategoryListItem
import com.google.gson.Gson

enum class CategoriesScreenStep { CATEGORIES_SCREEN, CREATE_CATEGORY_OVERLAY, ERROR_OVERLAY }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    nextScreen: (String) -> Unit,
    viewModel: CategoriesViewModel = hiltViewModel(),
    bottomBar: @Composable() (() -> Unit),
) {
    val screenState by viewModel.categoriesScreenState.collectAsState()
    var currentScreen by remember { mutableStateOf(CATEGORIES_SCREEN) }
    val currCategory = screenState.currCategory

    Scaffold(
        bottomBar = bottomBar,
        floatingActionButton = {
            Row {
                CustomFloatingButton(
                    visible = screenState.hasParent,
                    onClick = { currentScreen = CREATE_CATEGORY_OVERLAY }
                ) {
                    Icon(Icons.Filled.UploadFile, null)
                }

                val currCategoryJson = Gson().toJson(currCategory)
                CustomFloatingButton(
                    onClick = {
                        nextScreen(Route.CREATE_SERVICE.replace("{category}", currCategoryJson))
                    }) {
                    Icon(Icons.Filled.Add, null)
                }
            }
        },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Gray.copy(alpha = 0.2f),
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
                title = { Text(text = if (screenState.hasParent) currCategory.name else "Categories") },
                actions = {
                    if (screenState.isEditMode) {
                        CategoryOverflowMenu(
                            screenState = screenState,
                            viewModel = viewModel
                        )
                    } else {
                        IconButton(onClick = { /* do something */ }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                tint = MaterialTheme.colorScheme.onSurface,
                                contentDescription = "Search"
                            )
                        }
                    }
                },
            )
        }
    ) {
        CategoriesScreenContent(
            modifier = modifier.padding(it),
            viewModel = viewModel,
            currentScreen = currentScreen,
            nextScreen = nextScreen,
            screenState = screenState
        )
    }
}

@Composable
private fun CategoriesScreenContent(
    modifier: Modifier,
    viewModel: CategoriesViewModel,
    currentScreen: CategoriesScreenStep,
    nextScreen: (String) -> Unit,
    screenState: CategoriesScreenState
) {
    var currentScreen1 = currentScreen
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        HorizontalDivider(color = Color.Gray)
        val categories by viewModel.categories.collectAsState(emptyList())

        LazyColumn(userScrollEnabled = currentScreen1 == CATEGORIES_SCREEN) {
            item {
//                    CategoriesSection(viewModel = viewModel, categories = categories)
            }

            itemsIndexed(categories) { index, category ->

                CategoryListItem(
                    text = category.name,
                    onClick = {
                        println("Click on $category")
                        viewModel.onEvent(
                            CategoryScreenEvent.ShowCategory(categories[index])
                        )
                    },
                    handleSelect = {
                        viewModel.onEvent(
                            CategoryScreenEvent.UpdateCategorySelectedState(
                                category
                            )
                        )
                    },
                    isSelectMode = screenState.isEditMode,
                    isSelected = screenState.selectedItems.containsKey(category.id),
                    actionButton = {
                        if (!screenState.isEditMode) {
                            DropDownEditMenu(screenState, viewModel, category)
                        }
                    }
                )
            }
            item {
                ServicesSection(viewModel = viewModel, nextScreen = nextScreen)
            }
        }
    }

    if (currentScreen1 == CREATE_CATEGORY_OVERLAY) {
        CreateCategoryOverlay(
            onCancel = { currentScreen1 = CATEGORIES_SCREEN },
            onSave = { category ->
                viewModel.onEvent(CategoryScreenEvent.CreateCategory(category))
                currentScreen1 = CATEGORIES_SCREEN
            }
        )
    }

    if (screenState.errorMessage.isNotBlank()) {
        Dialog(
            onDismissRequest = { viewModel.onEvent(CategoryScreenEvent.HideError) }
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(modifier = Modifier.fillMaxWidth(), text = screenState.errorMessage)
                TextButton(onClick = { viewModel.onEvent(CategoryScreenEvent.HideError) }) {
                    Text(text = "Продовжити")
                }
            }
        }
    }

    if (screenState.hasParent) {
        BackHandler { viewModel.onEvent(CategoryScreenEvent.Back) }
    }
}
