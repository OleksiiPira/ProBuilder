package com.example.probuilder.presentation.screen.categories.categories

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.domain.model.ActionItems
import com.example.probuilder.domain.model.Category
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.components.CustomFloatingButton
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.screen.categories.categories.overflow_menu.MoreActionsButton
import com.example.probuilder.presentation.screen.categories.component.CategoryListItem
import com.example.probuilder.presentation.screen.categories.component.DropDownButton

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    nextScreen: (String) -> Unit,
    viewModel: CategoriesViewModel = hiltViewModel(),
    bottomBar: @Composable (() -> Unit),
) {
    val screenState by viewModel.screenState.collectAsState()
    val categories by viewModel.categories.collectAsState(emptyList())

    Scaffold(
        topBar = { TopBar(
                title = screenState.currCategory.name,
                moreActions = listOf(ActionItems("Видалити", { viewModel.removeCategories(screenState.selectedCategories) }, Icons.Delete)),
                onSelectAll = { viewModel.selectAllCategories(categories) },
                onSearch = {},
                isEditMode = screenState.isEditMode) },
        floatingActionButton = { CustomFloatingButton({ nextScreen(Route.CREATE_CATEGORY.replace("{parentId}", screenState.currCategory.id)) }) { Icons.Add } },
        bottomBar = bottomBar
    ) { padding ->
        CategoriesScreenContent(
            modifier = modifier.padding(padding),
            categories = categories,
            nextScreen = nextScreen,
            selectCategory = viewModel::selectCategory,
            removeCategory = viewModel::removeCategory,
            screenState = screenState
        )
    }
}

@Composable
private fun CategoriesScreenContent(
    modifier: Modifier,
    categories: List<Category>,
    nextScreen: (String) -> Unit,
    selectCategory: (Category) -> Unit,
    removeCategory: (Category) -> Unit,
    screenState: CategoriesScreenState,
) {
    LazyColumn(modifier = modifier.fillMaxSize().padding(vertical = 16.dp)) {
        items(categories) { category ->
            CategoryListItem(
                text = category.name,
                onClick = { nextScreen(Route.SERVICES.replace("{categoryId}", category.id).replace("{categoryName}", category.name)) },
                handleSelect = { selectCategory(category) },
                isSelectMode = screenState.isEditMode,
                isSelected = screenState.selectedCategories.contains(category),
                jobsCount = category.jobsCount,
                actionButton = {
                    if (!screenState.isEditMode) DropDownButton( content = {
                        DropdownMenuItem(leadingIcon = { Icons.Delete }, text = { Text(text = "Delete") }, onClick = { removeCategory(category) })
                    })
                }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(
    title: String,
    moreActions: List<ActionItems>,
    onSelectAll: () -> Unit,
    onSearch: () -> Unit,
    isEditMode: Boolean
) {
    val barColors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color(0xFF2A3C48),
        titleContentColor = Color.White)
    TopAppBar(
        colors = barColors,
        navigationIcon = { IconButton(onClick = { /* do something */ }) { Icons.Menu } },
        title = { Text(text = title) },
        actions = {
            if (!isEditMode) IconButton(onSearch) { Icons.Search }
            else MoreActionsButton(onSelectAll, moreActions)
        }
    )
}
