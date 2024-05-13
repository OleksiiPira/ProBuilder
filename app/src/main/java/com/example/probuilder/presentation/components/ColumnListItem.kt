package com.example.probuilder.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun ColumnListItem(modifier: Modifier = Modifier, title: String, value: String) {
    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = Typography.titleMedium
        )
        Text(
            modifier = Modifier.weight(1f),
            text = value,
            style = Typography.bodyLarge
        )
    }
}