package com.example.probuilder.presentation.screen.home_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun TextIconButton(
    modifier: Modifier = Modifier,
    iconImageVector: ImageVector,
    text: String,
    onButtonSelected: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable { onButtonSelected() }
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            imageVector = iconImageVector,
            contentDescription = text + " " + iconImageVector.name
        )
        Text(text = text)
    }
}