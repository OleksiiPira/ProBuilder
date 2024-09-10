package com.example.probuilder.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun TitleLarge(text: String = "", modifier: Modifier = Modifier) {
    Text(text = text, style = Typography.titleLarge, modifier = modifier)
}
@Composable
fun TitleMedium(text: String = "", modifier: Modifier = Modifier) {
    Text(text = text, style = Typography.titleMedium, modifier = modifier)
}
@Composable
fun TitleSmall(text: String = "", modifier: Modifier = Modifier) {
    Text(text = text, style = Typography.titleSmall, modifier = modifier)
}
@Composable
fun BodyLarge(text: String = "", modifier: Modifier = Modifier) {
    Text(text = text, style = Typography.bodyLarge, modifier = modifier)
}
@Composable
fun BodyMedium(text: String = "", fontWeight: FontWeight = FontWeight.Normal, modifier: Modifier = Modifier) {
    Text(text = text, style = Typography.bodyMedium, modifier = modifier)
}
@Composable
fun BodySmall(text: String = "", color: Color = Color(0xFF475259), modifier: Modifier = Modifier) {
    Text(text = text, style = Typography.bodySmall, modifier = modifier, color = color)
}
@Composable
fun LabelLarge(text: String = "", modifier: Modifier = Modifier) {
    Text(text = text, style = Typography.labelLarge, modifier = modifier)
}
@Composable
fun LabelMedium(text: String = "", color: Color = MaterialTheme.colorScheme.onSurface, modifier: Modifier = Modifier) {
    Text(text = text, style = Typography.labelMedium, color = color, modifier = modifier)
}
@Composable
fun LabelSmall(text: String = "", modifier: Modifier = Modifier) {
    Text(text = text, style = Typography.labelSmall, modifier = modifier)
}


