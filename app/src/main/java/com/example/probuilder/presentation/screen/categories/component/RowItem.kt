package com.example.probuilder.presentation.screen.categories.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.probuilder.domain.model.ButtonCfg
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun RowItem(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {},
    isSelectMode: Boolean = false,
    isSelected: Boolean = false,
    badgeNumber: Int = 0,
    handleSelect: () -> Unit = {},
    leadingIcon: @Composable () -> Unit = { Icons.ArrowRight },
    actionButton: @Composable (RowScope.() -> Unit) = {}
) {
    Row(
        modifier = modifier
            .heightIn(min = 56.dp)
            .fillMaxWidth()
            .background(if (isSelected) Color.LightGray else Color.Transparent)
            .pointerInput(isSelectMode) {
                detectTapGestures(
                    onTap = { if (!isSelectMode) onClick() else handleSelect() },
                    onLongPress = { handleSelect() },
                )
            }
            .padding(horizontal = 16.dp)
        ,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon()
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text, style = Typography.labelLarge)
            if (badgeNumber > 0) Row(
                modifier = Modifier
                    .clip(ButtonCfg.RoundedShape)
                    .background(Color(0xFFF5CE54))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(text = badgeNumber.toString(), style = Typography.labelSmall)
            }
        }

        actionButton()
    }
}
