package com.example.probuilder.presentation.screen.delete.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.probuilder.domain.model.delete.Project
import com.example.probuilder.presentation.screen.delete.home_screen.components.ProjectCard
import com.example.probuilder.presentation.screen.delete.home_screen.components.WelcomeCard

@Composable
fun HomeScreenContent(modifier: Modifier) {
    var projectSelected by remember { mutableStateOf(Project()) }
    
    if (projectSelected.address == "") {
        HomeContent(modifier) { project: Project -> projectSelected = project }
    } else {
        Button(onClick = { projectSelected = Project() }) {
            Text(text = "Unselect project")
        }
        Text(text = projectSelected.toString())
    }

}

@Composable
fun HomeContent(modifier: Modifier, onProjectSelected: (Project) -> Unit) {
    Spacer(modifier = Modifier.height(5.dp))
    Column(modifier = modifier.padding(horizontal = 12.dp)) {
        WelcomeCard()
        val projects = listOf(
            Project("вул.Тракт глинянський 89б", "20 квітня, 2023", "Піра Олексій"),
            Project(
                "вул.Дмитра Монастирського 146, квартира 16",
                "2 лютого, 2023",
                "Андрій Дмитрович"
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Активні Проекти",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "переглянути усі",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(projects) {
                ProjectCard(project = it, onProjectSelected)
            }
        }


    }
}

