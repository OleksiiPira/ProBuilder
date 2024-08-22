package com.example.probuilder.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier.fillMaxWidth().height(44.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color(0xFF0C1318)
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Text(text = text, style = Typography.labelLarge)
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        modifier = modifier.fillMaxWidth().height(44.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color(0xFF0C1318),
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp),
    ) {
        Text(text = text, style = Typography.labelLarge)
    }
}
