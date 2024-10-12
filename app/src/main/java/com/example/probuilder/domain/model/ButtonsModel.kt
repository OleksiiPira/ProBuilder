package com.example.probuilder.domain.model

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


object ButtonCfg {
    val RoundedShape: RoundedCornerShape = RoundedCornerShape(12.dp)
    val Height: Dp = 48.dp
    val OutlinedColors: ButtonColors @Composable get() = ButtonDefaults
        .buttonColors(
            contentColor = Color(0xFF0C1318),
            containerColor = Color.Transparent,
        )
}