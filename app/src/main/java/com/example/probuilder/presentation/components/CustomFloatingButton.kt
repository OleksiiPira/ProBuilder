package com.example.probuilder.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomFloatingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    visible: Boolean = true,
) {
    if(!visible) return
    FloatingActionButton(
        modifier = modifier.padding(16.dp),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color(0xFF303D45),
        onClick = onClick,
    ) {
        Icons.Add
    }
}