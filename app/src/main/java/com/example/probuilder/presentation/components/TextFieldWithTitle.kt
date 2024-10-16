package com.example.probuilder.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.probuilder.presentation.components.config.ButtonCfg
import com.example.probuilder.presentation.components.config.Paddings
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun TextFieldWithTitle(
    title: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit) = { Icon(imageVector = Icons.Outlined.Close, contentDescription = null) },
    shape: Shape = ButtonCfg.shape,
    horizPaddings: Dp = 16.dp
) {
    Column(modifier = modifier.padding(horizontal = horizPaddings)) {
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = title,
            style = Typography.titleSmall
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { onValueChange(it) },
            shape = shape,
            trailingIcon = trailingIcon,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFF9F9FA4),
                unfocusedPlaceholderColor = Color(0xFF475259),
                unfocusedTextColor = Color(0xFF475259)
            )
        )
    }
}


@Composable
fun NumericTextFieldWithTitle(
    modifier: Modifier = Modifier,
    title: String,
    value: Double,
    onValueChange: (Double) -> Unit = {},
    trailingIcon: @Composable (() -> Unit) = { Icon(imageVector = Icons.Outlined.Close, contentDescription = null) },
    shape: Shape = ButtonCfg.shape,
    horizPaddings: Dp = Paddings.DEFAULT
) {
    var inputValue by remember { mutableStateOf(value.toString()) }
    var isValid by remember { mutableStateOf(true) }
    LaunchedEffect(value) { inputValue = value.toString().replace(".0", "") }
    Column(modifier = modifier.padding(horizontal = horizPaddings)) {
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = title,
            style = Typography.titleSmall
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputValue,
            onValueChange = { newValue ->
                inputValue = newValue
                val doubleValue = newValue.toDoubleOrNull()
                isValid = doubleValue != null
                if (isValid) onValueChange(doubleValue!!)
            },
            shape = shape,
            trailingIcon = trailingIcon,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFF9F9FA4),
                unfocusedPlaceholderColor = Color(0xFF475259),
                unfocusedTextColor = Color(0xFF475259)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        if (!isValid && inputValue.isNotEmpty()) {
            Text(
                text = "Будь ласка, введіть просте число або дробове число",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}