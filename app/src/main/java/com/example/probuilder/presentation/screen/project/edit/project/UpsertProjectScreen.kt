package com.example.probuilder.presentation.screen.project.edit.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.probuilder.R
import com.example.probuilder.domain.model.Project
import com.example.probuilder.presentation.components.LabelMedium
import com.example.probuilder.presentation.components.Paddings
import com.example.probuilder.presentation.components.PrimaryButton
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.components.TextFieldWithTitle
import com.example.probuilder.presentation.screen.categories.categories.TopBar

@Composable
fun UpsertProjectScreen(
    viewModel: UpsertProjectViewModel = hiltViewModel(),
    bottomBar: @Composable () -> Unit,
    goBack: () -> Unit
) {
    val project by viewModel.project
    Scaffold(
        bottomBar = bottomBar,
        topBar = {
            TopBar(title = "Створити працівника", onNavigationPress = goBack) }
    ) { paddings ->
        val saveProjectDetails = {
            viewModel.saveProject()
            goBack()
        }

        Column(
            modifier = Modifier.padding(paddings).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            UpsertProjectScreenContent(project = project, onEvent = viewModel::onEvent)
            PrimaryButton(text = "Зберегти", onClick = saveProjectDetails)
            SecondaryButton(text = "Відмінити", onClick = goBack)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun UpsertProjectScreenContent(
    modifier: Modifier = Modifier,
    project: Project,
    onEvent: (UpsertProjectEvent) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = project.imageUrl,
            modifier = Modifier.height(170.dp),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.project_placeholder),
            error = painterResource(id = R.drawable.project_placeholder),
            contentDescription = stringResource(R.string.description),
            onSuccess = {  }
        )
        LabelMedium("Додати зображення", color = Color(0xFF1B2CCA), Modifier.padding(vertical = Paddings.DEFAULT))
        TextFieldWithTitle(
            title = "Назва роботи",
            value = project.name,
            onValueChange = { onEvent(UpsertProjectEvent.SetName(it)) },
        )
        TextFieldWithTitle(
            title = "Адреса",
            value = project.address,
            onValueChange = { onEvent(UpsertProjectEvent.SetAddress(it)) },
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            TextFieldWithTitle(
                modifier = Modifier.weight(1f),
                title = "Початок",
                value = project.startDate,
                onValueChange = { onEvent(UpsertProjectEvent.SetStartDate(it)) }
            )
            TextFieldWithTitle(
                modifier = Modifier.weight(1f),
                title = "Кінець",
                value = project.endDate,
                onValueChange = { onEvent(UpsertProjectEvent.SetEndDate(it)) }
            )
        }
        Spacer(Modifier.height(16.dp))
    }
}
