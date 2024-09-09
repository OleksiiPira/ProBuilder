package com.example.probuilder.presentation.screen.project.edit.worker

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
import com.example.probuilder.domain.model.Worker
import com.example.probuilder.presentation.components.PrimaryButton
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.components.TextFieldWithTitle
import com.example.probuilder.presentation.screen.categories.categories.TopBar

@Composable
fun UpsertWorkerScreen(
    modifier: Modifier = Modifier,
    viewModel: UpsertWorkerViewModel = hiltViewModel(),
    bottomBar: @Composable () -> Unit,
    goBack: () -> Unit
) {

    Scaffold(
        bottomBar = bottomBar,
        topBar = { TopBar(title = "Створити працівника", onNavigationPress = goBack) }
    ) { paddings ->
        val worker by viewModel.worker
        val saveWorker = {
            viewModel.saveProject()
            goBack()
        }

        Column(
            modifier = Modifier.padding(paddings).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
            UpsertWorkerScreenContent(worker = worker, onEvent = viewModel::onEvent)
            PrimaryButton(text = "Зберегти", onClick = saveWorker)
            SecondaryButton(text = "Відмінити", onClick = goBack)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun UpsertWorkerScreenContent(
    modifier: Modifier = Modifier,
    worker: Worker = Worker(),
    onEvent: (UpsertWorkerEvent) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldWithTitle("ПІБ", worker.name, { onEvent(UpsertWorkerEvent.SetName(it)) })
        TextFieldWithTitle("Номер телефону", worker.phone, { onEvent(UpsertWorkerEvent.SetPhone(it)) })
        TextFieldWithTitle("Електронна адреса", worker.email, { onEvent(UpsertWorkerEvent.SetEmail(it)) })
        TextFieldWithTitle("Додаткова інформація", worker.note, { onEvent(UpsertWorkerEvent.SetEmail(it)) })
        Spacer(modifier = Modifier.height(16.dp))
    }
}
