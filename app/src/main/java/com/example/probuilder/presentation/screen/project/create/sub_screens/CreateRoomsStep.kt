package com.example.probuilder.presentation.screen.project.create.sub_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.probuilder.R
import com.example.probuilder.domain.model.Room
import com.example.probuilder.presentation.components.BodyLarge
import com.example.probuilder.presentation.components.BodySmall
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.components.Paddings
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.components.TitleLarge
import com.example.probuilder.presentation.screen.categories.component.DropDownButton
import com.example.probuilder.presentation.screen.ui.theme.Typography

@Composable
fun CreateRoomsStep(
    modifier: Modifier = Modifier,
    rooms: List<Room> = listOf(Room(), Room(), Room()),
) {
    Column(
        modifier = modifier.padding(top = Paddings.DEFAULT),
    ) {
        if (rooms.isEmpty()) {
            TitleLarge(
                "Додайте інформацію про кімнати",
                Modifier.padding(horizontal = Paddings.DEFAULT)
            )
            Spacer(modifier = Modifier.height(4.dp))
            BodyLarge(
                "Щоб продовжити потрібно створити 1 кімнату. Згодом можна додати більше кімнат або внести необхідні зміни.",
                modifier = Modifier.padding(horizontal = Paddings.DEFAULT)
            )
        } else {
            rooms.forEachIndexed { index, room ->
                RoomCard(room)
                if (rooms.size > 1 && index != rooms.lastIndex) {
                    HorizontalDivider(
                        color = Color(0xFFB6B6BB),
                        modifier = Modifier.padding(horizontal = Paddings.DEFAULT)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(28.dp))
        SecondaryButton(
            text = "Додати кімнату ",
            onClick = { /*TODO*/ },
            Modifier.padding(horizontal = Paddings.DEFAULT)
        )
        Spacer(modifier = Modifier.height(28.dp))
    }
}

@Composable
fun RoomCard(
    room: Room = Room(),
    onClick: () -> Unit = {}
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = room.imageUrl,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .height(60.dp)
                    .width(72.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.project_placeholder),
                error = painterResource(id = R.drawable.project_placeholder),
                contentDescription = stringResource(R.string.description)
            )
            Column(Modifier.weight(1f)) {
                Text(text = room.name, style = Typography.titleSmall)
                Spacer(modifier = Modifier.height(4.dp))
                BodySmall("Роботи: 2000 000 грн")
                BodySmall("Матеріали: 2000 000 грн")
            }
            var expend by remember { mutableStateOf(false) }
            val onMoreClicked = { expend = !expend }
            DropDownButton(expend = expend, onClick = onMoreClicked) {
                DropdownMenuItem(
                    leadingIcon = { Icons.Delete },
                    text = { Text(text = "Delete") },
                    onClick = {
                        onMoreClicked()
                    })
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            LinearProgressIndicator(
                progress = { room.completeHours / room.totalHours },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color(0xFFEEEEF2)
            )

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
}