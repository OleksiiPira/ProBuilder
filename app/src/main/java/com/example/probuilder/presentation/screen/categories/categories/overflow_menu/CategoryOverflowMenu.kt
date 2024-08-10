package com.example.probuilder.presentation.screen.categories.categories.overflow_menu

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.probuilder.domain.model.ActionItems
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.screen.categories.component.DropDownButton

@Composable
fun MoreActionsButton(
    selectAll: () -> Unit,
    actionItems: List<ActionItems>
) {
    IconButton(onClick = selectAll) { Icons.SelectAll }
    DropDownButton() {
        actionItems.forEach { item ->
            DropdownMenuItem(leadingIcon = { item.icon }, text = { Text(text = item.text) }, onClick = item.onClick)
        }
    }
}