package com.example.probuilder.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.probuilder.R

@Composable
fun CustomFloatingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    icon: Int = R.drawable.add,
    containerColor: Color = MaterialTheme.colorScheme.primary
) {
    if(!visible) return
    FloatingActionButton(
        modifier = modifier.padding(16.dp),
        containerColor = containerColor,
        contentColor = Color(0xFF303D45),
        onClick = onClick,
    ) {
        Icon(painterResource(id = icon), contentDescription = null)
    }
}

@Composable
fun ProjectFloatingButton(
    modifier: Modifier = Modifier,
    pressed: Boolean = false,
    onClick: () -> Unit,
    content: @Composable (ColumnScope.() -> Unit)
) {
    Row(modifier = modifier) {
        Spacer(modifier = Modifier.weight(5F))
        Column(
            modifier = Modifier.weight(5F),
            horizontalAlignment = Alignment.End
        ) {
            if (pressed) {
                Column(
                    modifier = Modifier.padding(horizontal = Paddings.DEFAULT),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    content()
                }
            }
            CustomFloatingButton(
                icon = if(pressed) R.drawable.floating_btn_cross else R.drawable.add,
                containerColor = if (pressed) Color(0xFFC09A23) else MaterialTheme.colorScheme.primary,
                onClick = onClick)
        }
    }
}
