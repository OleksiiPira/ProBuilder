package com.example.probuilder.presentation.screen.project.details.worker

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.R
import com.example.probuilder.domain.model.Worker
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.components.BodyLarge
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.components.Paddings
import com.example.probuilder.presentation.components.Poster
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.components.SimpleNote
import com.example.probuilder.presentation.components.TitleMedium
import com.example.probuilder.presentation.screen.categories.categories.TopBar
import com.example.probuilder.presentation.screen.categories.component.DropDownButton
import com.example.probuilder.presentation.screen.categories.component.RowItem
import com.google.gson.Gson

@Composable
fun WorkerDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: WorkerDetailsViewModel = hiltViewModel(),
    bottomBar: @Composable () -> Unit,
    nextScreen: (String) -> Unit,
    goBack: () -> Unit
) {
    val worker by viewModel.worker.collectAsState(initial = Worker())
    val projectId by viewModel.projectId

    val showUpsertWorkerScreen = { nextScreen(Route.UPSERT_WORKER.replace("{projectId}", projectId).replace("{worker}", Gson().toJson(worker))) }

    Scaffold(
        bottomBar = bottomBar,
        topBar = {
            TopBar(title = "Працівник", onNavigationPress = goBack, trailingIcon = {}) }
    ) { paddings ->
        WorkerDetailsScreenContent(
            modifier = modifier.padding(paddings),
            worker = worker,
            editWorker = showUpsertWorkerScreen,
        )
    }
}

@Composable
private fun WorkerDetailsScreenContent(
    modifier: Modifier = Modifier,
    worker: Worker = Worker(),
    editWorker: () -> Unit = {},
    scrollState: ScrollState = rememberScrollState()
) {
    Column(
        modifier.verticalScroll(scrollState),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.padding(horizontal = Paddings.DEFAULT),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            Poster(imageUrl = worker.imageUrl, size = 80, radius = 40, placeHolder = R.drawable.avatar, modifier = Modifier.border(
                width = 1.dp,
                color = Color(0xFF9F9FA4),
                shape = RoundedCornerShape(40.dp)
            ))
            Column {
                BodyLarge(worker.trade)
                TitleMedium(worker.name)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        RowItem(
            leadingIcon = { Icons.Phone },
            text = worker.phone,
            onClick = {  },
            actionButton = {
                var expend by remember { mutableStateOf(false) }
                val onMoreClicked = { expend = !expend }
                DropDownButton(expend = expend, onClick = onMoreClicked) {
                    DropdownMenuItem(leadingIcon = { Icons.Delete }, text = { Text(text = "Delete") },
                        onClick = { onMoreClicked() })
                }
            }
        )
        RowItem(
            leadingIcon = { Icons.Email },
            text = worker.email,
            onClick = {  },
            actionButton = {
                var expend by remember { mutableStateOf(false) }
                val onMoreClicked = { expend = !expend }
                DropDownButton(expend = expend, onClick = onMoreClicked) {
                    DropdownMenuItem(leadingIcon = { Icons.Delete }, text = { Text(text = "Delete") },
                        onClick = {
                            onMoreClicked()
                        })
                }
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        TitleMedium("Додаткова інформація", Modifier.padding(horizontal = Paddings.DEFAULT))
        SimpleNote(text = worker.note)
        Spacer(modifier = Modifier.height(28.dp))
        SecondaryButton(text = "Редагувати", onClick = editWorker, Modifier.padding(horizontal = Paddings.DEFAULT))
    }
}

@Preview
@Composable
fun WorkerDetailsScreenContentPreview() {
    WorkerDetailsScreenContent(
        modifier = Modifier.background(Color.White),
        worker = Worker(note = "Особисті записи. Записуйте свої думки, почуття та події, які відбуваються у вашому житті. Особисті записи. Записуйте свої думки, почуття та події, які відбуваються у вашому житті.")
    )

}
