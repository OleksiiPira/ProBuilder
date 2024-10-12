package com.example.probuilder.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.probuilder.domain.model.ButtonCfg

@Composable
fun ProgressSmall(progress: Float) {
    LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(ButtonCfg.RoundedShape),
        color = MaterialTheme.colorScheme.primary,
        trackColor = Color(0xFFEEEEF2)
    )
}

@Composable
fun ProgressLarge(progress: Float) {
    LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier.fillMaxWidth().height(12.dp).clip(ButtonCfg.RoundedShape),
        color = MaterialTheme.colorScheme.primary,
        trackColor = Color(0xFFEEEEF2)
    )
}
