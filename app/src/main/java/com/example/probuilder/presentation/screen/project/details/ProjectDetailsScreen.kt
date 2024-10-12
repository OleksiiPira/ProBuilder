package com.example.probuilder.presentation.screen.project.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.R
import com.example.probuilder.common.ext.shadowBtn
import com.example.probuilder.common.ext.toJSON
import com.example.probuilder.domain.model.ActionItems
import com.example.probuilder.domain.model.Project
import com.example.probuilder.domain.model.Room
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.components.BodyLarge
import com.example.probuilder.presentation.components.DimmedBlockingOverlay
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.components.Note
import com.example.probuilder.presentation.components.Paddings
import com.example.probuilder.presentation.components.PrimaryButton
import com.example.probuilder.presentation.components.ProjectFloatingButton
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.components.TitleLarge
import com.example.probuilder.presentation.components.TitleMedium
import com.example.probuilder.presentation.screen.categories.component.DropDownButton
import com.google.gson.Gson

@Composable
fun ProjectDetailsScreen(
    nextScreen: (String) -> Unit,
    viewModel: ProjectDetailsViewModel = hiltViewModel(),
) {
    val project by viewModel.project.collectAsState(Project())
    val rooms by viewModel.rooms.collectAsState(emptyList())
    val showClientDetailsScreen = { nextScreen(Route.CLIENT_DETAILS.replace("{projectId}", project.id).replace("{client}", Gson().toJson(project.client))) }
    val showWorkerDetailsScreen = { workerId: String -> nextScreen(Route.WORKER_DETAILS.replace("{projectId}", project.id).replace("{workerId}", workerId)) }
    val showRoomDetailsScreen = { roomId: String -> nextScreen(Route.ROOM_DETAILS.replace("{projectId}", project.id).replace("{roomId}", roomId)) }

    val showCreateRoomScreen = { nextScreen(Route.UPSERT_ROOM.replace("{projectId}", project.id)) }

    var expendedButtons by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            ProjectFloatingButton(pressed = expendedButtons, onClick = { expendedButtons = !expendedButtons }){
                val buttonColor = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0B90A), contentColor = Color(0xFF0C1318))
                PrimaryButton(text = "Кімнату", onClick = showCreateRoomScreen, icon = R.drawable.room, colors = buttonColor, modifier = Modifier.shadowBtn())
                PrimaryButton(text = "Працівника", onClick = { /*TODO*/ }, icon = R.drawable.worker, colors = buttonColor, modifier = Modifier.shadowBtn())
                PrimaryButton(text = "Нотатку", onClick = { /*TODO*/ }, icon = R.drawable.note, colors = buttonColor, modifier = Modifier.shadowBtn())
            }
                               },
        ) { paddings ->

        ProjectScreenContent(
            modifier = Modifier.padding(paddings),
            project = project,
            rooms = rooms,
            showClientDetailsScreen = showClientDetailsScreen,
            showWorkerDetailsScreen = showWorkerDetailsScreen,
            showRoomDetailsScreen = showRoomDetailsScreen,
            deleteRoom = viewModel::deleteRoom,
            nextScreen = nextScreen
        )
        if (expendedButtons) {
            DimmedBlockingOverlay(dismiss = { expendedButtons = false })
            BackHandler { expendedButtons = false }
        }
    }
}

@Composable
fun ProjectScreenContent(
    modifier: Modifier = Modifier,
    project: Project,
    rooms: List<Room>,
    showClientDetailsScreen: () -> Unit,
    showWorkerDetailsScreen: (String) -> Unit,
    showRoomDetailsScreen: (String) -> Unit,
    deleteRoom: (String, String) -> Unit,
    nextScreen: (String) -> Unit
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        val modifierDefault = Modifier.padding(horizontal = Paddings.DEFAULT)
        DetailsScreenHero(project.imageUrl, project.totalHours, project.completeHours)
        TitleLarge(project.name, Modifier.padding(horizontal = Paddings.DEFAULT))
        Row(
            modifierDefault
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icons.LocationSmall
            BodyLarge(project.address)
        }

        UserCard(client = project.client, onClick = showClientDetailsScreen) { IconButton(showClientDetailsScreen) { Icons.ArrowRightLarge } }
        PricesInfo(totalJobsPrice = 400023, totalMaterialsPrice = 400500, totalPrice = 800523)

        Column(Modifier.padding(horizontal = Paddings.DEFAULT)) {
            PrimaryButton(text = "Сформувати кошторис", onClick = { /*TODO*/ })
            Spacer(modifier = Modifier.height(12.dp))
            SecondaryButton(text = "Сформувати фактуру", onClick = { /*TODO*/ })
        }

        Spacer(Modifier.height(32.dp))
        TitleMedium("Кімнати", modifierDefault)
        rooms.forEachIndexed { index, room ->
            RoomCard(room, { showRoomDetailsScreen(room.id) }, actionItems = listOf(
                ActionItems("Видалити", { deleteRoom(project.id, room.id) }),
                ActionItems("Редагувати", { nextScreen(Route.UPSERT_ROOM.replace("{projectId}", project.id).replace("{room}", room.toJSON())) })
            ))
            if (project.rooms.size > 1 && index != project.rooms.lastIndex) {
                HorizontalDivider(color = Color(0xFFB6B6BB), modifier = Modifier.padding(horizontal = Paddings.DEFAULT))
            }
        }

        Spacer(Modifier.height(20.dp))
        TitleMedium("Працівники", modifierDefault)
        project.workers.forEach {worker ->
            var expend by remember { mutableStateOf(false) }
            WorkerCard(worker, { showWorkerDetailsScreen(worker.id) }) {
                DropDownButton(expend = expend, onClick = { expend = !expend }){}
            }
        }

        Spacer(Modifier.height(20.dp))
        TitleMedium("Нотатки", modifierDefault)
        project.notes.forEach { note ->
            var expend by remember { mutableStateOf(false) }
            Note(note.name, note.description){
                DropDownButton(expend = expend, onClick = { expend = !expend }){}
            }
        }
    }

}
