package com.example.probuilder.presentation.screen.project.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.probuilder.R
import com.example.probuilder.common.ext.toUAH
import com.example.probuilder.domain.model.Client
import com.example.probuilder.domain.model.Room
import com.example.probuilder.domain.model.Worker
import com.example.probuilder.presentation.components.Badge
import com.example.probuilder.presentation.components.BodyLarge
import com.example.probuilder.presentation.components.BodyMedium
import com.example.probuilder.presentation.components.BodySmall
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.components.Paddings
import com.example.probuilder.presentation.components.Poster
import com.example.probuilder.presentation.components.ProgressLarge
import com.example.probuilder.presentation.components.ProgressSmall
import com.example.probuilder.presentation.components.TitleSmall
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun DetailsScreenHero(imageUrl: String, totalHours: Float, completeHours: Float) {
    Column(Modifier.padding(bottom = Paddings.DEFAULT), verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Box {
            var textColor by remember { mutableStateOf(Color(0xFF475259)) }
            var showShadow by remember { mutableStateOf(false) }
            AsyncImage(
                model = imageUrl,
                modifier = Modifier.height(240.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.project_placeholder),
                error = painterResource(id = R.drawable.project_placeholder),
                contentDescription = stringResource(R.string.description),
                onSuccess = {
                    showShadow = true
                    textColor = Color(0xFFEEEEF2)
                }
            )
            if (showShadow) Image(modifier = Modifier
                .align(Alignment.BottomCenter)
                .alpha(1F), painter = painterResource(id = R.drawable.bottom_shaddow), contentDescription = null)
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                ProgressLarge(progress = completeHours / totalHours)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    BodySmall("Прогрес", color = textColor, modifier = Modifier.weight(1f))
                    Text(text = completeHours.toString(), style = Typography.bodySmall, color = textColor, fontWeight = FontWeight.Bold)
                    BodySmall(" / $totalHours днів", color = textColor)
                }
            }
        }
    }
}

@Composable
fun UserCard(
    modifier: Modifier = Modifier,
    client: Client = Client(),
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .padding(horizontal = Paddings.DEFAULT, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Poster(imageUrl = client.imageUrl, size = 40, radius = 20, "Фото профіля")
        Column(Modifier.weight(1f)) {
            TitleSmall("Замовник:")
            BodyLarge(client.name)
        }
        content()
    }
}

@Composable
fun WorkerCard(
    worker: Worker = Worker(),
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = Paddings.DEFAULT, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Poster(imageUrl = worker.imageUrl, size = 40, radius = 20, "Фото профіля")
        Column(Modifier.weight(1f)) {
            TitleSmall(worker.trade)
            BodyLarge(worker.name)
        }
        content()
    }
}

@Composable
fun RoomCard(
    room: Room,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(Modifier
        .clickable { onClick() }
        .padding(vertical = 4.dp, horizontal = Paddings.DEFAULT)
    ) {
        Row(
            modifier = modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Poster(room.imageUrl, 60, 72, 4, "Фото кімнати")
            Column(Modifier.weight(1f)) {
                TitleSmall(room.name)
                BodySmall("Роботи: 200 000 грн")
                BodySmall("Матеріали:  200 000 грн")
            }
            IconButton(onClick) { Icons.MoreVert }
        }
        ProgressSmall(progress = room.completeHours / room.totalHours)
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            BodySmall("Прогрес", modifier = Modifier.weight(1f))
            BodySmall(room.completeHours.toString(), color = Color(0xFF0C1318))
            BodySmall(" / ${room.totalHours} днів")
        }
    }
}

@Composable
fun PricesInfo(modifier: Modifier = Modifier, totalJobsPrice: Int, totalMaterialsPrice: Int, totalPrice: Int) {
    Column(modifier.padding(horizontal = Paddings.DEFAULT).padding(top = 12.dp, bottom = 20.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                BodyMedium("Вартість робіт", fontWeight = FontWeight.Light)
                Badge("16")
            }
            BodyMedium(totalJobsPrice.toUAH(), fontWeight = FontWeight.Light)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                BodyMedium("Вартість матеріалів", fontWeight = FontWeight.Light)
                Badge("35")
            }
            BodyMedium(totalMaterialsPrice.toUAH(), fontWeight = FontWeight.Light)
        }
        HorizontalDivider(color = Color(0xFFB6B6BB), modifier = Modifier.padding(vertical = 4.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            BodyLarge("Загальна вартість", fontWeight = FontWeight.Normal)
            BodyLarge(totalPrice.toUAH(), fontWeight = FontWeight.Normal)
        }
    }
}