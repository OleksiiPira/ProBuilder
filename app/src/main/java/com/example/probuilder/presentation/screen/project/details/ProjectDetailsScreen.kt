package com.example.probuilder.presentation.screen.project.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.domain.model.Project
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.components.BodyMedium
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.components.Note
import com.example.probuilder.presentation.components.Paddings
import com.example.probuilder.presentation.components.PrimaryButton
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.components.TitleLarge
import com.example.probuilder.presentation.components.TitleMedium
import com.example.probuilder.presentation.screen.categories.categories.TopBar
import com.example.probuilder.presentation.screen.categories.component.DropDownButton
import com.google.gson.Gson

@Composable
fun ProjectDetailsScreen(
    bottomBar: @Composable () -> Unit = {},
    nextScreen: (String) -> Unit,
    goBack: () -> Unit = {},
    viewModel: ProjectDetailsViewModel = hiltViewModel(),
) {
    val project by viewModel.project.collectAsState(Project())
    val showClientDetailsScreen = { nextScreen(Route.CLIENT_DETAILS.replace("{projectId}", project.id).replace("{client}", Gson().toJson(project.client))) }

    Scaffold(
        bottomBar = bottomBar,
        topBar = { TopBar(title = project.name, onNavigationPress = goBack) }
    ) { paddings ->
        ProjectScreenContent(Modifier.padding(paddings), project, showClientDetailsScreen)
    }
}

@Composable
fun ProjectScreenContent(
    modifier: Modifier = Modifier,
    project: Project,
    showClientDetailsScreen: () -> Unit,
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        val modifierDefault = Modifier.padding(horizontal = Paddings.DEFAULT)
        ProjectHero(project)
        TitleLarge(project.name, modifierDefault)
        Row(
            modifierDefault
                .padding(top = 4.dp, bottom = Paddings.DEFAULT)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Icons.LocationSmall
            BodyMedium(project.address, fontWeight = FontWeight.Light)
        }


        UserCard(client = project.client, onClick = showClientDetailsScreen) { IconButton(showClientDetailsScreen) { Icons.ArrowRightLarge } }

        Column(Modifier.padding(Paddings.HorizontalPaddings)
        ) {
            PrimaryButton(text = "Сформувати кошторис", onClick = { /*TODO*/ })
            Spacer(modifier = Modifier.height(12.dp))
            SecondaryButton(text = "Сформувати фактуру", onClick = { /*TODO*/ })
        }

        Spacer(Modifier.height(20.dp))
        TitleMedium("Кімнати", modifierDefault)
        project.rooms.forEach { room -> RoomCard(room) }

        Spacer(Modifier.height(20.dp))
        TitleMedium("Працівники", modifierDefault)
        project.workers.forEach {worker ->
            var expend by remember { mutableStateOf(false) }
            WorkerCard(worker) {
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


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProjectDetailsScreenPrev() {
    ProjectScreenContent(
        Modifier,
        Project(),
    ){}
}
