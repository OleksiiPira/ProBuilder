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
    containerColor: Color = Color(0xFFF2F2F2),
    contentColor: Color = MaterialTheme.colorScheme.primary,
    visible: Boolean = true,
    content: @Composable () -> Unit
) {
    if(!visible) return
    FloatingActionButton(
        modifier = modifier.padding(16.dp),
        containerColor = containerColor,
        contentColor = contentColor,
        onClick = onClick,
    ) {
        content()
    }
}