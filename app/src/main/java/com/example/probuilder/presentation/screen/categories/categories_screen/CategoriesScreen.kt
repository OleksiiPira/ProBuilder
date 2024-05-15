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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.Service
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.components.CustomFloatingButton
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoryScreenEvent.CreateCategory
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoryScreenEvent.ShowCategory
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoryScreenEvent.UpdateCategorySelectedState
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoryScreenMode.SHOW_CREATE_CATEGORY
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoryScreenMode.SHOW_ERROR
import com.example.probuilder.presentation.screen.categories.categories_screen.drop_down_edit_menu.DropDownEditMenu
import com.example.probuilder.presentation.screen.categories.categories_screen.overflow_menu.CategoryOverflowMenu
import com.example.probuilder.presentation.screen.categories.categories_screen.services_section.ServicesSection
import com.example.probuilder.presentation.screen.categories.component.CategoryListItem
import com.google.gson.Gson

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    nextScreen: (String) -> Unit,
    viewModel: CategoriesViewModel = hiltViewModel(),
    bottomBar: @Composable (() -> Unit),
) {
    val screenState by viewModel.screenState.collectAsState()
    val services by viewModel.services.collectAsState(emptyList())
    val categories by viewModel.categories.collectAsState(emptyList())

    Scaffold(
        topBar = { TopBar(screenState, viewModel) },
        floatingActionButton = { FloatingActionButtons(screenState, nextScreen) },
        bottomBar = bottomBar
    ) {
        CategoriesScreenContent(
            modifier = modifier.padding(it),
            categories = categories,
            services = services,
            nextScreen = nextScreen,
            onEvent = { event: CategoryScreenEvent -> viewModel.onEvent(event) },
            screenState = screenState
        )
    }
}



@Composable
private fun CategoriesScreenContent(
    modifier: Modifier,
    categories: List<Category>,
    services: List<Service>,
    nextScreen: (String) -> Unit,
    screenState: CategoriesScreenState,
    onEvent: (CategoryScreenEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        userScrollEnabled = !screenState.isOverlayShown
    ) {
        item { HorizontalDivider(color = Color.Gray) }
        itemsIndexed(categories) { index, category ->
            CategoryListItem(
                text = category.name,
                onClick = { onEvent(ShowCategory(categories[index])) },
                handleSelect = { onEvent(UpdateCategorySelectedState(category)) },
                isSelectMode = screenState.isEditMode,
                isSelected = screenState.selectedItems.containsKey(category.id),
                actionButton = { if (!screenState.isEditMode) DropDownEditMenu(onEvent, category) }
            )
        }
        item {
            ServicesSection(
                services = services,
                nextScreen = nextScreen,
                onEvent = onEvent,
                screenState = screenState
            )
        }
    }

    if (screenState.screenMode == SHOW_CREATE_CATEGORY) CreateCategoryOverlay(
        onCancel = screenState::hideOverlays,
        onSave = { onEvent(CreateCategory(it)) }
    )

    if (screenState.screenMode == SHOW_ERROR) ShowError(screenState)

    if (screenState.hasParent) BackHandler { onEvent(CategoryScreenEvent.Back) }
}

@Composable
private fun ShowError(
    screenState: CategoriesScreenState
) {
    Dialog(onDismissRequest = screenState::hideOverlays) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(modifier = Modifier.fillMaxWidth(), text = screenState.errorMessage)
            TextButton(onClick = screenState::hideOverlays) {
                Text(text = "Продовжити")
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(
    screenState: CategoriesScreenState,
    viewModel: CategoriesViewModel
) {
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
        title = { Text(text = if (screenState.hasParent) screenState.currCategory.name else "Categories") },
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

@Composable
private fun FloatingActionButtons(screenState: CategoriesScreenState, nextScreen: (String) -> Unit) {
    Row {
        CustomFloatingButton(
            visible = screenState.hasParent,
            onClick = screenState::showCreateCategory
        ) {
            Icon(Icons.Filled.UploadFile, null)
        }

        val currCategoryJson = Gson().toJson(screenState.currCategory)
        CustomFloatingButton({
            nextScreen(
                Route.CREATE_SERVICE.replace("{category}", currCategoryJson)
            )
        }) {
            Icon(Icons.Filled.Add, null)
        }
    }
}
