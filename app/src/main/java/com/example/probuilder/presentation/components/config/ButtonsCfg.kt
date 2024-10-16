package com.example.probuilder.presentation.components.config

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


object ButtonCfg {
    val shape: CornerBasedShape = Shapes().medium
    val height: Dp = 48.dp
    val outlinedColors: ButtonColors @Composable get() = ButtonDefaults
        .buttonColors(
            contentColor = Color(0xFF0C1318),
            containerColor = Color.Transparent,
        )
}