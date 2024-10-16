package com.example.probuilder.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.probuilder.domain.model.ButtonCfg
import com.example.probuilder.presentation.components.config.TextFieldCfg
import com.example.probuilder.presentation.components.config.contentPadding
import com.example.probuilder.presentation.components.config.contentWidth
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color(0xFF0C1318)
    ),
    icon: Int = -1
) {
    TextButton(
        modifier = modifier.width(contentWidth()).height(ButtonCfg.height),
        colors = colors,
        shape = ButtonCfg.shape,
        onClick = onClick
    ) {
        val iconPresent = icon > -1
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (iconPresent) Arrangement.spacedBy(8.dp) else Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (iconPresent) Icon(painterResource(id = icon), text)
            Text(text = text, style = Typography.labelLarge)
        }
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        modifier = modifier.width(contentWidth()).height(ButtonCfg.height),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color(0xFF0C1318),
            containerColor = Color.Transparent
        ),
        shape = ButtonCfg.shape,
    ) {
        Text(text = text, style = Typography.labelLarge)
    }
}

@Composable
fun FrameButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = ButtonCfg.shape,
    content: @Composable (RowScope.() -> Unit)
) {
    TextButton(
        modifier = modifier
            .fillMaxWidth()
            .height(TextFieldCfg.height)
            .border(1.dp, Color(0xFF9F9FA4), shape),
        shape = shape,
        colors = ButtonCfg.OutlinedColors,
        contentPadding = contentPadding(),
        onClick = onClick
    ) {
        content()
    }
}

@Composable
fun FixedButtonBackground(
    modifier: Modifier,
    content: @Composable () -> Unit

) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .background(Color(0xFFF5F5F5)))
        content()
    }
}
