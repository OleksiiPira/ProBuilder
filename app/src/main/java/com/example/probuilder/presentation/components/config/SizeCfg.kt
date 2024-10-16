package com.example.probuilder.presentation.components.config

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Paddings {
    val DEFAULT = 16.dp
    val EMPTY = 0.dp
}

@Composable
fun contentPadding(vertical: Dp = 0.dp): PaddingValues {
    return PaddingValues(horizontal = Paddings.DEFAULT, vertical = vertical)
}
@Composable
fun contentWidth(): Dp {
    val screenSize = screenSize()
    return screenSize.width - Paddings.DEFAULT * 2
}

@Composable
fun screenSize(): ScreenSize {
    val configuration = LocalConfiguration.current
    return ScreenSize(
        height = configuration.screenHeightDp.dp,
        width = configuration.screenWidthDp.dp
    )
}

data class ScreenSize(val height: Dp, val width: Dp)