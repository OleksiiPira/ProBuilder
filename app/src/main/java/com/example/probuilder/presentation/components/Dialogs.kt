package com.example.probuilder.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.probuilder.domain.enums.DisplayableEnum
import com.example.probuilder.presentation.components.config.ButtonCfg
import com.example.probuilder.presentation.components.config.Paddings
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun DialogOptions(
    modifier: Modifier = Modifier,
    options: List<DisplayableEnum>,
    dismiss: () -> Unit,
    onOptionSelected: (DisplayableEnum) -> Unit
)  {
    val onItemClick = { option: DisplayableEnum ->
        dismiss()
        onOptionSelected(option)
    }
    Dialog(onDismissRequest = dismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Text(
                text = "Оберіть варіант",
                modifier = Modifier.padding(vertical = Paddings.DEFAULT).fillMaxWidth(),
                style = Typography.titleMedium,
                textAlign = TextAlign.Center,
            )
            options.forEach { option ->
                HorizontalDivider(modifier.padding(horizontal = Paddings.DEFAULT))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ButtonCfg.height)
                        .clickable { onItemClick(option) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = modifier.fillMaxWidth(),
                        text = option.label,
                        style = Typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}