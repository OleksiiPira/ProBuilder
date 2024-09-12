package com.example.probuilder.presentation.screen.categories.categories

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.domain.model.ActionItems
import com.example.probuilder.domain.model.Category
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.components.CustomFloatingButton
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.screen.categories.categories.overflow_menu.MoreActionsButton
import com.example.probuilder.presentation.screen.categories.component.RowItem
import com.example.probuilder.presentation.screen.categories.component.DropDownButton
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    nextScreen: (String) -> Unit,
    goBack: () -> Unit,
    viewModel: CategoriesViewModel = hiltViewModel(),
    bottomBar: @Composable () -> Unit
) {
    val screenState by viewModel.screenState.collectAsState()
    val categories by viewModel.categories.collectAsState(emptyList())

    Scaffold(
        topBar = { TopBar(
                title = screenState.currCategory.name,
                moreActions = listOf(ActionItems("Видалити", { viewModel.removeCategories(screenState.selectedCategories) }, Icons.Delete)),
                onNavigationPress = goBack,
                onSelectAll = { viewModel.selectAllCategories(categories) },
                isEditMode = screenState.isEditMode) },
        floatingActionButton = { CustomFloatingButton({ nextScreen(Route.CREATE_CATEGORY.replace("{parentId}", screenState.currCategory.id)) }) },
        bottomBar = bottomBar
    ) { padding ->
        CategoriesScreenContent(
            modifier = modifier.padding(padding),
            categories = categories,
            nextScreen = nextScreen,
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
    removeCategory: (Category) -> Unit,
    screenState: CategoriesScreenState,
) {
    LazyColumn(modifier = modifier.fillMaxSize().padding(vertical = 16.dp)) {
        items(categories) { category ->
            RowItem(
                text = category.name,
                onClick = { nextScreen(Route.SERVICES.replace("{categoryId}", category.id).replace("{categoryName}", category.name)) },
                isSelectMode = screenState.isEditMode,
                isSelected = screenState.selectedCategories.contains(category),
                badgeNumber = category.jobsCount,
                actionButton = {
                    var expend by remember { mutableStateOf(false) }
                    val onMoreClicked = { expend = !expend }
                    if (!screenState.isEditMode) DropDownButton(expend = expend, onClick = onMoreClicked) {
                        DropdownMenuItem(leadingIcon = { Icons.Delete }, text = { Text(text = "Delete") },
                            onClick = {
                                removeCategory(category)
                                onMoreClicked()
                            })
                    }
                }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(
    title: String = "",
    moreActions: List<ActionItems> = emptyList(),
    onNavigationPress: () -> Unit = {},
    onSelectAll: () -> Unit = {},
    onSearch: () -> Unit = {},
    isEditMode: Boolean = false,
    leadingIcon: @Composable () -> Unit = { Icons.ArrowBack },
    trailingIcon: @Composable () -> Unit = { Icons.Search },
) {
    val barColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        titleContentColor = Color(0xFFEEEEF2),
        navigationIconContentColor = Color(0xFFEEEEF2),
    )
    TopAppBar(
        colors = barColors,
        navigationIcon = { IconButton(onNavigationPress) { leadingIcon() } },
        title = { Text(modifier = Modifier.fillMaxWidth(), text = title, style = Typography.titleMedium, textAlign = TextAlign.Center) },
        actions = {
            if (!isEditMode) IconButton(onSearch) { trailingIcon() }
            else if (moreActions.isNotEmpty()) MoreActionsButton(onSelectAll, moreActions)
        }
    )
}
