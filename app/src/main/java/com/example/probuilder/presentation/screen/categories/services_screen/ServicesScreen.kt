package com.example.probuilder.presentation.screen.categories.services_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.common.Constants
import com.example.probuilder.domain.model.ActionItems
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.screen.categories.categories.CategoriesScreenState
import com.example.probuilder.presentation.screen.categories.categories.overflow_menu.MoreActionsButton
import com.example.probuilder.presentation.screen.categories.component.ServiceListItem
import com.google.gson.Gson

@Composable
fun ServicesScreen(
    modifier: Modifier = Modifier,
    nextScreen: (String) -> Unit,
    viewModel: ServicesViewModel = hiltViewModel(),
    bottomBar: @Composable() (() -> Unit)
) {
    val jobs by viewModel.jobs.collectAsState(listOf())
    val state by viewModel.state.collectAsState(ServicesScreenState())
    Scaffold(
        bottomBar = bottomBar,
        floatingActionButton = {
            FloatingActionButton(
                modifier = modifier.padding(16.dp),
                containerColor = Color(0xFFF2F2F2),
                contentColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    val currCategoryJson = Gson().toJson(state.currCategory)
                    nextScreen(Route.CREATE_SERVICE.replace("{category}", currCategoryJson))
                          },
            ) { Icons.Add }
        },
        topBar = {
            TopBar(
                title = state.currCategory.name,
                actionItems = listOf(),
                onSelectAll = { /*TODO*/ },
                isEditMode = state.isEditMode)
        }
    ) { paddings ->
        Box(modifier = modifier
            .padding(paddings)
            .padding(Constants.HORIZONTAL_PADDING)
            .fillMaxSize()) {
            LazyColumn(modifier = modifier) {
                item {
                    Row {
                        Button(onClick = { /*TODO*/ }) { Text(text = "Всі") }
                        Button(onClick = { /*TODO*/ }) { Text(text = "Стіна") }
                        Button(onClick = { /*TODO*/ }) { Text(text = "Стеля") }
                        Button(onClick = { /*TODO*/ }) { Text(text = "Пілога") }
                    }
                }
                items(jobs) { job ->
                    HorizontalDivider(color = Color.Gray)
                    ServiceListItem(
                            job = job,
                            nextScreen = nextScreen,
                            screenState = CategoriesScreenState(),
                            removeJob = { viewModel.removeJobs(listOf(job)) }
                        )
                        HorizontalDivider(color = Color.Gray)
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(
    title: String,
    actionItems: List<ActionItems>,
    onSelectAll: () -> Unit,
    isEditMode: Boolean
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        navigationIcon = {
            IconButton(onClick = { /* do something */ }) { Icons.ArrowBack }
        },
        title = { Text(text = title) },
        actions = {
            if (isEditMode) {
                MoreActionsButton(selectAll = onSelectAll, actionItems = actionItems)
            } else {
                IconButton(onClick = { /* do something */ }) {
                    Icons.Search
                }
            }
        },
    )
}
