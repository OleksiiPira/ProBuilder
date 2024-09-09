package com.example.probuilder.presentation.screen.project.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.presentation.components.Paddings
import com.example.probuilder.presentation.components.PrimaryButton
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.screen.categories.categories.TopBar
import com.example.probuilder.presentation.screen.project.edit.client.UpsertClientScreenContent
import com.example.probuilder.presentation.screen.project.edit.project.UpsertProjectScreenContent

@Composable
fun CreateProjectScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateProjectViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val project by viewModel.project
    val client by viewModel.client
    val worker by viewModel.worker
    val projectStep by viewModel.projectStep
    val nextStepButtonTitle = if (projectStep != ProjectStep.CreateWorkers) "Далі" else "Зберегти"
    var goNextStep by remember { mutableStateOf({}) }
    var goPrevStep by remember { mutableStateOf({}) }
    Scaffold(
        bottomBar = {
            Column(modifier = Modifier
                .padding(horizontal = Paddings.DEFAULT)
                .padding(bottom = 40.dp),) {
                Row(horizontalArrangement = Arrangement.spacedBy(168.dp)) {
                    SecondaryButton(text = "Назад", onClick = goPrevStep, modifier.weight(1f))
                    PrimaryButton(text = nextStepButtonTitle, onClick = goNextStep, modifier.weight(1f))
                }
            }
        },
        topBar = {
            TopBar(title = "Створити проєкт", onNavigationPress = goBack) }
    ) { paddings ->

        val contentModifier = Modifier.padding(paddings).verticalScroll(rememberScrollState())

        when(projectStep) {
            ProjectStep.CreateProject -> {
                UpsertProjectScreenContent(contentModifier, project, viewModel::projectEvent)
                goPrevStep = goBack
                goNextStep = { viewModel.setProjectStep(ProjectStep.CreateClient) }
            }
            ProjectStep.CreateClient -> {
                UpsertClientScreenContent(contentModifier, client, viewModel::clientEvent)
                goPrevStep = { viewModel.setProjectStep(ProjectStep.CreateProject) }
                goNextStep = { viewModel.setProjectStep(ProjectStep.CreateRooms) }
            }
            ProjectStep.CreateRooms -> {
                goPrevStep = { viewModel.setProjectStep(ProjectStep.CreateClient) }
                goNextStep = { viewModel.setProjectStep(ProjectStep.CreateWorkers) }
            }
            ProjectStep.CreateWorkers -> {
//                UpsertWorkerScreenContent(contentModifier, worker, viewModel::workerEvent)
                goPrevStep = { viewModel.setProjectStep(ProjectStep.CreateRooms) }
                goNextStep = {
                    viewModel.saveProject()
                    goBack()
                }
            }
        }
    }
}
