package com.example.probuilder.common.ext

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.probuilder.domain.model.ButtonCfg

fun Modifier.shadowBtn(shape: RoundedCornerShape = ButtonCfg.RoundedShape): Modifier {
    return this.shadow(elevation = 3.dp, shape = shape, clip = false)
}

fun Modifier.blockActions(block: Boolean = true, dismiss: () -> Unit): Modifier {
    if (!block) return this
    return this.pointerInput(Unit) { detectTapGestures { dismiss() } }
}
