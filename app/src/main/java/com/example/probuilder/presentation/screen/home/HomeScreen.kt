package com.example.probuilder.presentation.screen.home

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.screen.categories.categories.TopBar
import com.example.probuilder.presentation.screen.delete.home_screen.components.ProjectCard
import com.example.probuilder.presentation.screen.delete.home_screen.components.WelcomeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    nextScreen: (String) -> Unit,
    bottomBar: @Composable() (() -> Unit)
) {
    var state by remember { mutableStateOf(0) }
    val titles = listOf("Tab 1", "Tab 2", "Tab 3 with lots of text")
    Scaffold(
        topBar = {
            TopBar(
                title = "Pro Builder",
                onNavigationPress = {},
                leadingIcon = { Icons.Menu },
                trailingIcon = { Icons.Search },
                moreActions = listOf()
            )
        },
        bottomBar = bottomBar
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Column(modifier = modifier.padding(it).padding(horizontal = 12.dp)) {

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
                    ProjectCard(project = it, {})//onProjectSelected)
                }
            }
        }
    }
}
