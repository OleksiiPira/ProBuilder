package com.example.probuilder.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.PopupProperties
import com.example.probuilder.domain.enums.DisplayableEnum
import com.example.probuilder.presentation.components.config.contentWidth

@Composable
fun CustomDropDownMenu(
    onSelected: (DisplayableEnum) -> Unit,
    modifier: Modifier = Modifier,
    defaultItem: DisplayableEnum? = null,
    items: List<DisplayableEnum> = emptyList(),
    width: Dp = contentWidth(),
) {
    var expanded by remember { mutableStateOf(false) }
    val selectItem = { item: DisplayableEnum ->
        onSelected(item)
        expanded = false
    }
    Shapes().medium
    Box(
        modifier = modifier.width(width),
        contentAlignment = Alignment.Center
    ) {
        FrameButton(onClick = { expanded = !expanded }) {
            if (defaultItem != null) LabelLarge(defaultItem.label, modifier = Modifier.weight(1f))
            Icons.ArrowDown
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(focusable = true, dismissOnBackPress = true, dismissOnClickOutside = true,),
            modifier = Modifier.background(Color(0xFFF5F5F5)).width(width)
        ) {
            items.forEach {
                    val isNotSameItem = it.label.isNotEmpty() && it != defaultItem
                    if (isNotSameItem) DropdownMenuItem(
                        onClick = { selectItem(it) },
                        text = { Text(text = it.label, textAlign = TextAlign.Center, modifier = modifier.fillMaxWidth()) }
                    )
                }
        }
    }
}
