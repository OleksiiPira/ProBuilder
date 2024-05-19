package com.example.probuilder.presentation.screen.categories.categories_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.probuilder.domain.model.Category
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun CreateCategoryScreen(
    modifier: Modifier = Modifier,
    onCancel: () -> Unit,
    onSave: (Category) -> Unit
) {
    var categoryName by rememberSaveable { mutableStateOf("") }
    Dialog(onDismissRequest = onCancel) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Створити тип роботи",
                style = Typography.titleLarge
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = categoryName,
                onValueChange = { categoryName = it },
                shape = RoundedCornerShape(8.dp),
                trailingIcon = { Icon(imageVector = Icons.Outlined.Close, contentDescription = null) },
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { onCancel() }) {
                    Text(
                        text = "Відмінити",
                        style = Typography.labelLarge
                    )
                }
                TextButton(onClick = { onSave(Category(name = categoryName)) }) {
                    Text(
                        text = "Зберегти",
                        style = Typography.labelLarge
                    )
                }
            }
        }
    }
}