package com.example.probuilder.presentation.screen.categories.categories_screen.overflow_menu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material.icons.outlined.SelectAll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoriesScreenState
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoriesViewModel
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoryScreenEvent
import com.example.probuilder.presentation.screen.categories.categories_screen.ItemState
import com.example.probuilder.presentation.screen.categories.component.DropDownButton

@Composable
fun CategoryOverflowMenu(
    screenState: CategoriesScreenState,
    viewModel: CategoriesViewModel
) {
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
                    imageVector = if (screenState.itemsMode == ItemState.FAVORITE) Icons.Outlined.FavoriteBorder else Icons.Filled.Favorite,
                    contentDescription = null
                )
            },
            text = { Text(text = if (screenState.itemsMode == ItemState.FAVORITE) "Видалити з улюблених" else "Додати в улюблені") },
            onClick = { viewModel.onEvent(CategoryScreenEvent.FavoriteSelectedCategory) }
        )
        DropdownMenuItem(
            leadingIcon = {
                Icon(
                    imageVector = if (screenState.itemsMode == ItemState.HIDED) Icons.Outlined.RemoveRedEye else Icons.Filled.RemoveRedEye,
                    contentDescription = null
                )
            },
            text = { Text(text = if (screenState.itemsMode == ItemState.HIDED) "Показати" else "Приховати") },
            onClick = { viewModel.onEvent(CategoryScreenEvent.HideSelectedCategories) }
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