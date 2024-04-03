package com.example.probuilder.presentation.screen.delete.home_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.probuilder.R

@Composable
fun WelcomeCard() {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .height(130.dp)
    ) {
        Row(modifier = Modifier.padding(start = 18.dp)) {
            Column(Modifier.weight(3F)) {
                Text(
                    modifier = Modifier
                        .padding(top = 25.dp, bottom = 10.dp),
                    text = "Вітаю!",
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                )
                Text(text = "Давай створимо новий проект")
            }
            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .weight(2F),
                painter = painterResource(id = R.drawable.construction_create_newproject),
                contentDescription = "Постер створити новий проект"
            )
        }
    }
}
