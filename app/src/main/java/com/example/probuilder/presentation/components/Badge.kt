package com.example.probuilder.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.probuilder.presentation.components.config.ButtonCfg
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun Badge(text: String) {
    Row(
        modifier = Modifier
            .clip(ButtonCfg.shape)
            .background(Color(0xFFF5CE54))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(text = text, style = Typography.labelSmall)
    }
}