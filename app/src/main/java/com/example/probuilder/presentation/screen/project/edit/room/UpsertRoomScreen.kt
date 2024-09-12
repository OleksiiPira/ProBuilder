package com.example.probuilder.presentation.screen.project.edit.room

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.domain.model.Room
import com.example.probuilder.presentation.components.PrimaryButton
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.components.TextFieldWithTitle
import com.example.probuilder.presentation.screen.categories.categories.TopBar

@Composable
fun UpsertRoomScreen(
    viewModel: UpsertRoomViewModel = hiltViewModel(),
    bottomBar: @Composable () -> Unit,
    goBack: () -> Unit
) {
    Scaffold(
        bottomBar = bottomBar,
        topBar = { TopBar(title = "Створити працівника", onNavigationPress = goBack) }
    ) { paddings ->
        val room by viewModel.room
        val saveRoom = {
            viewModel.saveRoom()
            goBack()
        }

        Column(
            modifier = Modifier
                .padding(paddings)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            UpsertRoomScreenContent(room = room, onEvent = viewModel::onEvent)
            PrimaryButton(text = "Зберегти", onClick = saveRoom)
            SecondaryButton(text = "Відмінити", onClick = goBack)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun UpsertRoomScreenContent(
    modifier: Modifier = Modifier,
    room: Room = Room(),
    onEvent: (UpsertRoomEvent) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldWithTitle("Назва кімнати", room.name, { onEvent(UpsertRoomEvent.SetName(it)) })
        Spacer(modifier = Modifier.height(16.dp))
    }
}
