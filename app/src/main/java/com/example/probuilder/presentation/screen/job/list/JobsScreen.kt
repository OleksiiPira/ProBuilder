package com.example.probuilder.presentation.screen.job.list

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.probuilder.presentation.components.config.ButtonCfg
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.components.CustomFloatingButton
import com.example.probuilder.presentation.components.Icons
import com.example.probuilder.presentation.screen.categories.categories.CategoriesScreenState
import com.example.probuilder.presentation.screen.categories.categories.TopBar
import com.example.probuilder.presentation.screen.categories.component.ServiceListItem
import com.example.probuilder.presentation.screen.ui.theme.Typography
import com.google.gson.Gson

@Composable
fun JobsScreen(
    modifier: Modifier = Modifier,
    nextScreen: (String) -> Unit,
    goBack: () -> Unit,
    viewModel: JobsScreenViewModel = hiltViewModel(),
    bottomBar: @Composable () -> Unit
) {
    val jobs by viewModel.jobs.observeAsState(emptyList())
    val tags by viewModel.tags.observeAsState(emptyList())
    val state by viewModel.state.collectAsState(JobsScreenState())
    Scaffold(
        bottomBar = bottomBar,
        floatingActionButton = { CustomFloatingButton({ nextScreen(Route.CREATE_SERVICE.replace("{category}", Gson().toJson(state.currCategory))) }) },
        topBar = {
            TopBar(
                title = state.currCategory.name,
                moreActions = listOf(),
                onNavigationPress = goBack,
                onSelectAll = { /*TODO*/ },
                onSearch = {},
                isEditMode = state.isEditMode)
        }
    ) { paddings ->
        val horizontalPadding = 16.dp
        Box(modifier = modifier
            .padding(paddings)
            .fillMaxSize()) {
            LazyColumn(modifier = modifier) {
                item {
                    LazyRow(Modifier.padding(horizontalPadding, vertical = 8.dp),horizontalArrangement = Arrangement.spacedBy(4.dp)) {
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
                            paddingValue = PaddingValues(start = horizontalPadding, top = 12.dp, bottom = 12.dp)
                        )
                        HorizontalDivider(Modifier.padding(horizontal = horizontalPadding),color = Color(0xFFB6B6BB))
                    }
                }
            }
        }
    }
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
            .border(1.dp, borderColor, ButtonCfg.shape)
            .clip(ButtonCfg.shape)
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