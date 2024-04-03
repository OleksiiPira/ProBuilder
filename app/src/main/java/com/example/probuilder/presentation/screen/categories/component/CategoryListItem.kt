package com.example.probuilder.presentation.screen.categories.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun CategoryListItem(
    modifier: Modifier = Modifier,
    imageVector: ImageVector = Icons.Filled.PlayArrow,
    text: String,
    onClick: () -> Unit,
    handleSelect: () -> Unit,
    isSelectMode: Boolean = false,
    isSelected: Boolean = false,
    actionButton: @Composable() (RowScope.() -> Unit) = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(1.dp))
            .background(if (isSelected) Color.LightGray else Color.Transparent)
            .padding(vertical = 6.dp)
            .pointerInput(isSelectMode) {
                detectTapGestures(
                    onTap = { if (!isSelectMode) onClick() else handleSelect() },
                    onLongPress = { handleSelect() },
                )
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .heightIn(min = 48.dp),
            imageVector = imageVector,
            tint = Color.Black,
            contentDescription = "Localized description"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = text,
            style = Typography.labelLarge,
        )
        actionButton()
    }
    Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().background(color = Color.LightGray))
}
