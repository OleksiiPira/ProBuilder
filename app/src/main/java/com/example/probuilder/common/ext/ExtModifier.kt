package com.example.probuilder.common.ext

.border

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.probuilder.domain.model.ButtonCfg


fun Modifier.outlined(): Modifier {
    return this.fillMaxWidth().height(56.dp)
        .border(1.dp, Color(0xFF9F9FA4), ButtonCfg.RoundedShape)
}