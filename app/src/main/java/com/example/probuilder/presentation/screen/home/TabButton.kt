package com.example.probuilder.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun TabButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String = "Button",
    isSelected: Boolean = false,
    isUpdated: Boolean = false
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy((-4).dp)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White ,
                contentColor = if(!isUpdated) Color.Black else Color.Green
            ),
            contentPadding = PaddingValues(2.dp),
            shape = RoundedCornerShape(0)
        ) {
            Text(
                text = text,
                style = Typography.labelLarge,
            )
        }
        if (isSelected) {
            HorizontalDivider(color = Color.Gray)
        }
    }
}
