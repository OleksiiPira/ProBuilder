package com.example.probuilder.presentation.screen.categories.categories_screen.drop_down_edit_menu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.probuilder.domain.model.Category
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoryScreenEvent
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoryScreenEvent.FavoriteCategory
import com.example.probuilder.presentation.screen.categories.categories_screen.ItemState
import com.example.probuilder.presentation.screen.categories.component.DropDownButton

@Composable
fun DropDownEditMenu(
    onEvent: (CategoryScreenEvent) -> Unit,
    category: Category
) {
    DropDownButton {
        DropdownMenuItem(
            leadingIcon = { Icon(Icons.Outlined.Edit,null) },
            text = { Text(text = "Edit") },
            onClick = {})
        DropdownMenuItem(
            leadingIcon = {
                val icon = if (category.state == ItemState.FAVORITE) Icons.Outlined.Favorite else Icons.Filled.Favorite
                Icon(icon, contentDescription = null)
            },
            text = { Text(text = "Edit") },
            onClick = { onEvent(FavoriteCategory(category)) })

        DropdownMenuItem(
            leadingIcon = { Icon(Icons.Outlined.RemoveRedEye,null) },
            text = { Text(text = "Hide") },
            onClick = {
//                onEvent(CategoryScreenEvent.Hide(category))
            })

        DropdownMenuItem(
            leadingIcon = { Icon(Icons.Outlined.Delete,null) },
            text = { Text(text = "Delete") },
            onClick = { })
    }
}