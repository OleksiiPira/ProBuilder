package com.example.probuilder.presentation.screen.project.edit.worker

import androidx.compose.foundation.ScrollState
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
import com.example.probuilder.common.Constants
import com.example.probuilder.domain.model.ActionItems
import com.example.probuilder.domain.model.Worker
import com.example.probuilder.presentation.components.Icons
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
    val worker by viewModel.worker
    Scaffold(
        bottomBar = bottomBar,
        topBar = {
            TopBar(
                title = "Створити працівника",
                moreActions = listOf(ActionItems("Видалити", {  }, Icons.Delete)),
                onSelectAll = {},
                onSearch = {},
                onNavigationPress = goBack,
                isEditMode = false) }
    ) { paddings ->
        val scrollState = rememberScrollState()
        EditWorkerScreenContent(modifier.padding(paddings), scrollState, worker, viewModel::onEvent, viewModel::saveProject, goBack)
    }
}

@Composable
private fun EditWorkerScreenContent(
    modifier: Modifier,
    scrollState: ScrollState,
    worker: Worker,
    onEvent: (UpsertWorkerEvent) -> Unit,
    saveProject: () -> Unit,
    goBack: () -> Unit
) {
    Column(
        modifier = modifier.padding(Constants.HORIZONTAL_PADDING).verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
    ) {
        TextFieldWithTitle("ПІБ", worker.name, { onEvent(UpsertWorkerEvent.SetName(it)) })
        TextFieldWithTitle("Номер телефону", worker.phone, { onEvent(UpsertWorkerEvent.SetPhone(it)) })
        TextFieldWithTitle("Електронна адреса", worker.email, { onEvent(UpsertWorkerEvent.SetEmail(it)) })
        TextFieldWithTitle("Додаткова інформація", worker.note, { onEvent(UpsertWorkerEvent.SetEmail(it)) })

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(text = "Зберегти", onClick = {
            saveProject()
            goBack()
        })

        SecondaryButton(text = "Відмінити", onClick = goBack)
    }
}
