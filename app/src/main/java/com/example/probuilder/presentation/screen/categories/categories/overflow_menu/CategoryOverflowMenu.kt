package com.example.probuilder.presentation.screen.categories.categories.overflow_menu

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.probuilder.domain.model.ActionItems
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.screen.categories.component.DropDownButton

@Composable
fun MoreActionsButton(
    selectAll: () -> Unit,
    actionItems: List<ActionItems>
) {
    var expend by remember { mutableStateOf(false) }
    val onMoreClicked = { expend = !expend }
    IconButton(onClick = selectAll) { Icons.SelectAll }
    DropDownButton(expend = expend, onClick = onMoreClicked) {
        actionItems.forEach { item ->
            DropdownMenuItem(leadingIcon = { item.icon }, text = { Text(text = item.text) }, onClick = item.onClick)
        }
    }
}