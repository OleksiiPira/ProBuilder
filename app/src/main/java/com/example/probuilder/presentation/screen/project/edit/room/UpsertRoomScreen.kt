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
import com.example.probuilder.domain.model.RoomSurface
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
    var step by remember { mutableStateOf(Create.ROOM) }
    val backActions = mapOf(Create.ROOM to { goBack() }, Create.SURFACE to { step = Create.ROOM })
    Scaffold(
        topBar = { TopBar(title = "Створити кімнати", onNavigationPress = { backActions[step]?.invoke() }) }
    ) { paddings ->
        val room by viewModel.room
        val surface by viewModel.surface

        val saveRoom = { viewModel.roomEvent(UpsertRoomEvent.Save); goBack(); }
        val saveSurface = { viewModel.surfaceEvent(SurfaceEvent.Save); step = Create.ROOM  }
        val deleteSurface = { surfaceItem: RoomSurface -> viewModel.surfaceEvent(SurfaceEvent.Delete(surfaceItem)) }
        val saveButtonLabel = mapOf(
            Create.ROOM to stringResource(R.string.save_btn),
            Create.SURFACE to stringResource(R.string.add_btn)
        )

        Box(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize()
        ) {
            when (step) {
                Create.ROOM -> UpsertRoomContent(
                    projectId = viewModel.projectId,
                    room = room,
                    addMeasurement = { step = Create.SURFACE },
                    onEvent = viewModel::roomEvent,
                    deleteSurface = deleteSurface
                )
                Create.SURFACE -> UpsertSurfaceContent(
                    surface = surface,
                    onEvent = viewModel::surfaceEvent,
                )
            }

            FixedButtonBackground(Modifier.align(Alignment.BottomCenter)){
                PrimaryButton(
                    text = saveButtonLabel[step] ?: stringResource(R.string.save_btn),
                    onClick = if (step == Create.ROOM) saveRoom else saveSurface,
                    modifier = Modifier.padding(Paddings.DEFAULT, 4.dp)
                )
            }
        }
    }
}

enum class Create { ROOM, SURFACE }
