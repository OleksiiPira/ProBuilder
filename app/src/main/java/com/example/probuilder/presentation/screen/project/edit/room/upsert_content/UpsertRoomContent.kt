package com.example.probuilder.presentation.screen.project.edit.room.upsert_content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.probuilder.R
import com.example.probuilder.domain.model.ButtonCfg
import com.example.probuilder.domain.model.Room
import com.example.probuilder.domain.model.RoomSurface
import com.example.probuilder.presentation.components.BodyLarge
import com.example.probuilder.presentation.components.LabelMedium
import com.example.probuilder.presentation.components.Paddings
import com.example.probuilder.presentation.components.RoomSurfaces
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.components.TextFieldWithTitle
import com.example.probuilder.presentation.components.TitleMedium
import com.example.probuilder.presentation.screen.project.edit.room.UpsertRoomEvent

@Composable
fun UpsertRoomContent(
    modifier: Modifier = Modifier,
    projectId: String,
    room: Room = Room(),
    addMeasurement: () -> Unit,
    onEvent: (UpsertRoomEvent) -> Unit,
    deleteSurface: (RoomSurface) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(WindowInsets.ime.asPaddingValues())
            .padding(bottom = ButtonCfg.Height + 24.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = room.imageUrl,
                modifier = Modifier.height(170.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.project_placeholder),
                error = painterResource(id = R.drawable.project_placeholder),
                contentDescription = stringResource(R.string.description),
                onSuccess = { }
            )
            LabelMedium(
                text = stringResource(R.string.add_image_btn),
                color = Color(0xFF1B2CCA),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        TextFieldWithTitle(
            title = stringResource(R.string.room_name_title),
            value = room.name,
            onValueChange = { onEvent(UpsertRoomEvent.SetName(it)) },
            modifier = modifier.padding(vertical = 24.dp)
        )

        TitleMedium(stringResource(R.string.measures_title),
            modifier
                .padding(bottom = 4.dp)
                .padding(horizontal = Paddings.DEFAULT))
        if (room.surfaces.isEmpty()) {
            BodyLarge(stringResource(R.string.add_mesure_later_description), modifier = Modifier.padding(horizontal = Paddings.DEFAULT))
        } else {
            RoomSurfaces(
                room = room,
                projectId = projectId,
                navigateTo = { },
                deleteSurface = deleteSurface
            )
        }

        SecondaryButton(
            text = stringResource(R.string.add_measure_btn),
            onClick = addMeasurement,
            modifier = modifier.padding(vertical = Paddings.DEFAULT, horizontal = Paddings.DEFAULT)
        )
    }

}