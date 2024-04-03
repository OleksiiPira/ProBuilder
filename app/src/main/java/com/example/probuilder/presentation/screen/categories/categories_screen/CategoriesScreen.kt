package com.example.probuilder.presentation.screen.categories.categories_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material.icons.outlined.SelectAll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.domain.model.Category
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.screen.categories.categories_screen.InvoiceFormState.CATEGORIES_SCREEN
import com.example.probuilder.presentation.screen.categories.component.CategoryListItem
import com.example.probuilder.presentation.screen.categories.component.DropDownButton
import com.example.probuilder.presentation.screen.ui.theme.Typography

enum class InvoiceFormState { CATEGORIES_SCREEN, CREATE_CATEGORY_OVERLAY, ERROR_OVERLAY }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    nextScreen: (String) -> Unit,
    categoryName: String = "Категорії",
    categoryId: String = "",
    viewModel: CategoriesViewModel = hiltViewModel(),
    bottomBar: @Composable() (() -> Unit)
) {
    viewModel.onEvent(CategoryScreenEvent.ShowCategory(categoryId))
    val rows by viewModel.categories.collectAsState(emptyList())
    val categoriesState by viewModel.categoriesScreenState.collectAsState()
    if (rows.isEmpty()) return
    var currentScreen by remember { mutableStateOf(CATEGORIES_SCREEN) }
    Scaffold(
        bottomBar = bottomBar,
        floatingActionButton = {
            FloatingActionButton(
                modifier = modifier.padding(16.dp),
                containerColor = Color(0xFFF2F2F2),
                contentColor = MaterialTheme.colorScheme.primary,
                onClick = { currentScreen = InvoiceFormState.CREATE_CATEGORY_OVERLAY },
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Gray.copy(alpha = 0.2f),//MaterialTheme.colorScheme.surface,
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
                title = { Text(text = categoryName) },
                actions = {
                    if (!categoriesState.isSelectingMode) {
                        IconButton(onClick = { /* do something */ }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                tint = MaterialTheme.colorScheme.onSurface,
                                contentDescription = "Search"
                            )
                        }
                    } else {
                        IconButton(onClick = { viewModel.onEvent(CategoryScreenEvent.SelectAll) }) {
                            Icon(
                                imageVector = Icons.Outlined.SelectAll,
                                contentDescription = null
                            )
                        }
                        DropDownButton() {
                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        imageVector = if (categoriesState.itemsMode == ItemState.FAVORITE) Icons.Outlined.FavoriteBorder else Icons.Filled.Favorite,
                                        contentDescription = null
                                    )
                                },
                                text = { Text(text = if (categoriesState.itemsMode == ItemState.FAVORITE) "Видалити з улюблених" else "Додати в улюблені") },
                                onClick = { viewModel.onEvent(CategoryScreenEvent.FavoriteSelectedCategory)}
                            )
                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        imageVector = if (categoriesState.itemsMode == ItemState.HIDED) Icons.Outlined.RemoveRedEye else Icons.Filled.RemoveRedEye,
                                        contentDescription = null
                                    )
                                },
                                text = { Text(text = if (categoriesState.itemsMode == ItemState.HIDED) "Показати" else "Приховати") },
                                onClick = {viewModel.onEvent(CategoryScreenEvent.HideSelectedCategories)}
                            )
                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Delete,
                                        contentDescription = null
                                    )
                                },
                                text = { Text(text = "Видалити") },
                                onClick = {}
                            )
                        }

                    }
                },
            )
        }
    ) {
        CategoriesScreenContent(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            rows = rows,
            nextScreen = nextScreen,
            userScrollEnabled = currentScreen == CATEGORIES_SCREEN,
            viewModel = viewModel,
            categoriesState = categoriesState
        )

        if (currentScreen == InvoiceFormState.CREATE_CATEGORY_OVERLAY) {
            CreateCategoryOverlay(
                onCancel = { currentScreen = CATEGORIES_SCREEN },
                onSave = { category ->
                    viewModel.onEvent(CategoryScreenEvent.CreateCategory(category))
                    currentScreen = CATEGORIES_SCREEN
                }
            )
        }
        if (categoriesState.errorMessage.isNotBlank()) {
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
                    Text(modifier = Modifier.fillMaxWidth(),text = categoriesState.errorMessage)
                    TextButton(onClick = { viewModel.onEvent(CategoryScreenEvent.HideError) }) {
                        Text(text = "Продовжити")
                    }
                }
            }
        }
    }

}

@Composable
private fun CategoriesScreenContent(
    modifier: Modifier,
    rows: List<Category>,
    nextScreen: (String) -> Unit,
    userScrollEnabled: Boolean,
    viewModel: CategoriesViewModel,
    categoriesState: CategoriesScreenState
) {
    val orderedCategories = mapOf(
        ItemState.FAVORITE to rows.filter { it.state == ItemState.FAVORITE },
        ItemState.DEFAULT to rows.filter { it.state == ItemState.DEFAULT },
        ItemState.HIDED to rows.filter { it.state == ItemState.HIDED }
    )

    Column(modifier = modifier) {
        HorizontalDivider(color = Color.Gray)
        LazyColumn(userScrollEnabled = userScrollEnabled) {
            orderedCategories.forEach { (state, categories) ->
                item {
                    CategorySection(
                    state = state,
                    categories = categories,
                    nextScreen = nextScreen,
                    viewModel = viewModel,
                    categoriesState = categoriesState
                ) }
            }

        }
    }
}

@Composable
private fun CategorySection(
    state: ItemState,
    categories: List<Category>,
    nextScreen: (String) -> Unit,
    viewModel: CategoriesViewModel,
    categoriesState: CategoriesScreenState
) {
    val expandedStates = remember { mutableStateMapOf<ItemState, Boolean>(
        ItemState.FAVORITE to true,
        ItemState.DEFAULT to true,
        ItemState.HIDED to false
    ) }
    if (categories.isNotEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray.copy(alpha = 0.1f))
                .clickable { expandedStates[state] = !expandedStates[state]!! },
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                modifier = Modifier
                    .padding(14.dp)
                    .weight(1f),
                text = when (state) {
                    ItemState.FAVORITE -> "Favorites items"
                    ItemState.DEFAULT -> "Default items"
                    ItemState.HIDED -> "Hided items"
                },
                textAlign = TextAlign.Center,
                style = Typography.titleMedium
            )
            Icon(
                modifier = Modifier.padding(14.dp),
                imageVector = if (expandedStates[state] == true) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = "Expand/Collapse"
            )
        }
        HorizontalDivider(color = Color.Gray)
    }

    if (expandedStates[state] == true) {
        categories.forEach { category ->
            CategoryListItem(
                text = category.name,
                onClick = { goToNextScreen(nextScreen, category) },
                handleSelect = {
                    viewModel.onEvent(CategoryScreenEvent.UpdateCategorySelectedState(category))
                },
                isSelectMode = categoriesState.isSelectingMode,
                isSelected = categoriesState.selectedItems.containsKey(category.id),
                actionButton = {
                    println(state.name)
                    if (!categoriesState.isSelectingMode) {
                        DropDownEditMenu(categoriesState, viewModel, category)
                    }
                }
            )
            HorizontalDivider(color = Color.Gray)
        }
    }
}

@Composable
private fun DropDownEditMenu(
    categoriesState: CategoriesScreenState,
    viewModel: CategoriesViewModel,
    category: Category
) {
    DropDownButton() {
        DropdownMenuItem(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = null
                )
            },
            text = { Text(text = "Edit") },
            onClick = {})
        DropdownMenuItem(
            leadingIcon = {
                Icon(
                    imageVector = if (category.state == ItemState.FAVORITE) Icons.Outlined.Favorite else Icons.Filled.Favorite,
                    contentDescription = null
                )
            },
            text = { Text(text = "Edit") },
            onClick = {
                viewModel.onEvent(
                    CategoryScreenEvent.FavoriteCategory(
                        category
                    )
                )
            })
        DropdownMenuItem(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.RemoveRedEye,
                    contentDescription = null
                )
            },
            text = { Text(text = "Hide") },
            onClick = { viewModel.onEvent(CategoryScreenEvent.HideCategory(category)) })
        DropdownMenuItem(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null
                )
            },
            text = { Text(text = "Delete") },
            onClick = { })
    }
}


private fun goToNextScreen(
    nextScreen: (String) -> Unit,
    category: Category
) {
    nextScreen(
        Route.SUB_CATEGORY
            .replace("{categoryId}", category.id)
            .replace("{categoryName}", category.name)
    )
}
