package com.example.probuilder.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.probuilder.domain.model.ButtonCfg

@Composable
fun Note(
    title: String,
    text: String,
    rightButton: @Composable () -> Unit
) {
    Column(
        Modifier
            .padding(horizontal = Paddings.DEFAULT, vertical = 6.dp)
            .clip(ButtonCfg.RoundedShape)
            .background(Color(0xFFEBEBF0))
            .padding(start = 12.dp)
            .padding(bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleSmall(title)
            rightButton()
        }
        Spacer(Modifier.height(4.dp))
        BodyMedium(text, fontWeight = FontWeight.Light, Modifier.padding(end = Paddings.DEFAULT))
    }
}

@Composable
fun SimpleNote(text: String) {
    Column(
        Modifier
            .padding(horizontal = Paddings.DEFAULT, vertical = 6.dp)
            .clip(ButtonCfg.RoundedShape)
            .background(Color(0xFFEBEBF0))
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        BodyMedium(
            if (text.isNotEmpty()) text else "Цей розділ поки порожній. Додайте інформацію, якщо потрібно.",
            fontWeight = FontWeight.Light,
            Modifier.padding(end = Paddings.DEFAULT)
        )
    }
}

@Preview
@Composable
fun Prev() {
    Column {
        SimpleNote(text = "Особисті записи. Записуйте свої думки, почуття та події, які відбуваються у вашому житті.")
        Note(
            title = "Для працівника Івана",
            text = "Особисті записи. Записуйте свої думки, почуття та події, які відбуваються у вашому житті."
        ) {

        }
    }
}