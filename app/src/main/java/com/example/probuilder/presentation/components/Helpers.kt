package com.example.probuilder.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import com.example.probuilder.common.ext.blockActions

@Composable
fun DimmedBlockingOverlay(
    modifier: Modifier = Modifier,
    show: Boolean = true,
    color: Color = Color(0x40000000),
    opacity: Float = 0.25F,
    dismiss: () -> Unit
) {
    if (show) Box(
        modifier = modifier
            .fillMaxSize()
            .background(color)
            .alpha(opacity)
            .blockActions(show, dismiss)
    ){

    }
}
