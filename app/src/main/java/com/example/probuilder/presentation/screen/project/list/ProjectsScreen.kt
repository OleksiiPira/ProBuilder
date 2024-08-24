package com.example.probuilder.presentation.screen.project.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.probuilder.R
import com.example.probuilder.domain.model.ActionItems
import com.example.probuilder.domain.model.Project
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.components.CustomFloatingButton
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.screen.categories.categories.TopBar
import com.example.probuilder.presentation.screen.categories.component.DropDownButton
import com.example.probuilder.presentation.screen.ui.theme.Typography
import com.google.gson.Gson


@Composable
fun ProjectList(
        bottomBar: @Composable (() -> Unit),
        nextScreen: (String) -> Unit,
        viewModel: ProjectsViewModel = hiltViewModel()
    ){
    val projects by viewModel.projects.collectAsState(initial = emptyList())
    Scaffold(
        topBar = { TopBar(
            title = "Мої проєкти",
            moreActions = listOf(ActionItems("Видалити", {  }, Icons.Delete)),
            onSelectAll = {  },
            onSearch = {},
            onNavigationPress = {},
            isEditMode = false) },
        floatingActionButton = { CustomFloatingButton({ nextScreen(Route.CREATE_PROJECTS) }) },
        bottomBar = bottomBar
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)){
            items(projects) { project ->
                val showProjectDetails = { nextScreen(Route.PROJECT_DETAILS.replace("{project}", Gson().toJson(project)))}
                ProjectCard(project, showProjectDetails, viewModel::removeProject)
            }
        }
    }
}

@Composable
fun ProjectCard(
    project: Project,
    onClick: () -> Unit = {},
    onDelete: (Project) -> Unit = {}
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = project.imageUrl,
                modifier = Modifier.clip(RoundedCornerShape(4.dp)).height(60.dp).width(72.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.project_placeholder),
                error = painterResource(id = R.drawable.project_placeholder),
                contentDescription = stringResource(R.string.description)
            )
            Column(Modifier.weight(1f)) {
                Text(text = project.name, style = Typography.titleSmall)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icons.LocationSmall
                    Text(text = project.address, style = Typography.bodySmall)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icons.ProfileSmall
                    Text(text = project.clientName, style = Typography.bodySmall)
                }
            }
            var expend by remember { mutableStateOf(false) }
            val onMoreClicked = { expend = !expend }
            DropDownButton(expend = expend, onClick = onMoreClicked){
                    DropdownMenuItem(leadingIcon = { Icons.Delete }, text = { Text(text = "Delete") }, onClick = {
                        onMoreClicked()
                        onDelete(project)
                    })
                }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Вартість робіт:", style = Typography.bodyMedium, fontWeight = FontWeight.Light)
            Text(text = "${project.price} грн", style = Typography.bodyMedium)
        }
        LinearProgressIndicator(
            progress = { project.progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = Color(0xFFEEEEF2)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(modifier = Modifier.weight(1f), text = "Початок: ${project.startDate}", style = Typography.bodySmall, fontWeight = FontWeight.Light)
            Text(text = "Завершення:", style = Typography.bodySmall)
            Text(text = "${project.endDate}:", style = Typography.bodySmall, color = Color.Green)
        }
    }
}

@Preview
@Composable
fun ProjectListPreview(){
    ProjectList(bottomBar = { /*TODO*/ }, nextScreen = {})
}