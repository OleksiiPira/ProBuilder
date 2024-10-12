package com.example.probuilder.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import com.example.probuilder.common.ext.blockActions

@Composable
fun DimmedBlockingOverlay(
    modifier: Modifier = Modifier,
    show: Boolean = true,
    dismiss: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0x40000000)).alpha(0.25F)
            .blockActions(show, dismiss)
    ){

    }
}
