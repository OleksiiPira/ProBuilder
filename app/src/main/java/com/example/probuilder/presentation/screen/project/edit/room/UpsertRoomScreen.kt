package com.example.probuilder.presentation.screen.project.edit.room

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.R
import com.example.probuilder.presentation.components.FixedButtonBackground
import com.example.probuilder.presentation.components.Paddings
import com.example.probuilder.presentation.components.PrimaryButton
import com.example.probuilder.presentation.screen.categories.categories.TopBar
import com.example.probuilder.presentation.screen.project.edit.room.upsert_content.UpsertRoomContent
import com.example.probuilder.presentation.screen.project.edit.room.upsert_content.UpsertSurfaceContent
import com.example.probuilder.presentation.screen.project.edit.room.UpsertSurfaceEvent as SurfaceEvent

@Composable
fun UpsertRoomScreen(
    viewModel: UpsertRoomViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    Scaffold(
        topBar = { TopBar(title = "Створити кімнати", onNavigationPress = goBack) }
    ) { paddings ->
        val room by viewModel.room
        val surface by viewModel.surface
        var step by remember { mutableStateOf(Create.ROOM) }

        val saveRoom = { viewModel.roomEvent(UpsertRoomEvent.Save); goBack(); }
        val saveSurface = { viewModel.surfaceEvent(SurfaceEvent.Save); step = Create.ROOM  }

        Box(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize()
                .padding(bottom = Paddings.DEFAULT)
        ) {
            when (step) {
                Create.ROOM -> UpsertRoomContent(
                    room = room,
                    addMeasurement = { step = Create.SURFACE },
                    onEvent = viewModel::roomEvent
                )
                Create.SURFACE -> UpsertSurfaceContent(
                    surface = surface,
                    onEvent = viewModel::surfaceEvent,
                    goBack = goBack
                )
            }

            FixedButtonBackground(Modifier.align(Alignment.BottomCenter))
            PrimaryButton(
                text = stringResource(R.string.save_btn),
                onClick = if (step == Create.ROOM) saveRoom else saveSurface,
                modifier = Modifier.padding(Paddings.DEFAULT, 4.dp).align(Alignment.BottomCenter)
            )

        }
    }
}

enum class Create { ROOM, SURFACE }
