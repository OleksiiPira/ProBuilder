package com.example.probuilder.presentation.screen.categories.jobs_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.domain.model.ActionItems
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.screen.categories.categories.CategoriesScreenState
import com.example.probuilder.presentation.screen.categories.categories.overflow_menu.MoreActionsButton
import com.example.probuilder.presentation.screen.categories.component.ServiceListItem
import com.example.probuilder.presentation.screen.ui.theme.Typography
import com.google.gson.Gson

@Composable
fun JobsScreen(
    modifier: Modifier = Modifier,
    nextScreen: (String) -> Unit,
    viewModel: JobsScreenViewModel = hiltViewModel(),
    bottomBar: @Composable() (() -> Unit)
) {
    val jobs by viewModel.jobs.observeAsState(emptyList())
    val tags by viewModel.tags.observeAsState(emptyList())
    val state by viewModel.state.collectAsState(JobsScreenState())
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
        val HORIZONTAL_PADDING = 16.dp
        Box(modifier = modifier
            .padding(paddings)
            .fillMaxSize()) {
            LazyColumn(modifier = modifier) {
                item {
                    LazyRow(Modifier.padding(HORIZONTAL_PADDING, vertical = 8.dp),horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        items(tags){ TagButton(it) { viewModel.selectTag(it) } }
                    }
                }

                items(jobs) { job ->
                    if (state.selectedTags.isEmpty() || state.selectedTags.find { job.tags.contains(it) } != null) {
                        ServiceListItem(
                            job = job,
                            nextScreen = nextScreen,
                            screenState = CategoriesScreenState(),
                            removeJob = { viewModel.removeJobs(listOf(job)) },
                            paddingValue = PaddingValues(start = HORIZONTAL_PADDING, top = 12.dp, bottom = 12.dp)
                        )
                        HorizontalDivider(Modifier.padding(horizontal = HORIZONTAL_PADDING),color = Color(0xFFB6B6BB))
                    }
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

@Composable
fun TagButton(
    text: String,
    onClick : () -> Unit,
) {
    var enabled by remember { mutableStateOf(false) }
    val borderColor = if(!enabled) Color(0xFF757D83) else Color.Transparent
    Row(
        modifier = Modifier
            .height(32.dp)
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onClick()
                enabled = !enabled
            }
            .background(if (enabled) Color(0xEE9EB6C8) else Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 8.dp)
        ,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (enabled) Icons.Check
        Text(text, style = Typography.labelMedium)
    }
}