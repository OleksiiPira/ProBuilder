package com.example.probuilder.presentation.screen.categories.categories.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.probuilder.presentation.screen.categories.categories.ItemState
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun ServicesTitleRow(
    expanded: Boolean,
    onPress: (ItemState) -> Unit,
    state: ItemState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray.copy(alpha = 0.1f))
            .clickable { onPress(state) },
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier
                .padding(14.dp)
                .weight(1f),
            text = getRowTitle(state),
            textAlign = TextAlign.Center,
            style = Typography.titleMedium
        )
        Icon(
            modifier = Modifier.padding(14.dp),
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = "Expand/Collapse"
        )
    }
    HorizontalDivider(color = Color.Gray)
}

@Composable
private fun getRowTitle(state: ItemState) =
    when (state) {
        ItemState.FAVORITE -> "Favorites items"
        ItemState.DEFAULT -> "Default items"
        ItemState.HIDED -> "Hided items"
    }