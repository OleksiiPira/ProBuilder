package com.example.probuilder.presentation.screen.categories.services_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.common.Constants
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.screen.categories.categories_screen.ItemState
import com.example.probuilder.presentation.screen.categories.component.ServiceListItem
import com.example.probuilder.presentation.screen.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(
    modifier: Modifier = Modifier,
    nextScreen: (String) -> Unit,
    viewModel: ServicesViewModel = hiltViewModel(),
    bottomBar: @Composable() (() -> Unit)
) {
    val prices by viewModel.currJobs.observeAsState(listOf())
    if (prices.isEmpty()) {
        viewModel.updatePrices()
        return
    }
    Scaffold(
        bottomBar = bottomBar,
        floatingActionButton = {
            FloatingActionButton(
                modifier = modifier.padding(16.dp),
                containerColor = Color(0xFFF2F2F2),
                contentColor = MaterialTheme.colorScheme.primary,
                onClick = {nextScreen(Route.CREATE_SERVICE)},
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Main manu"
                        )
                    }
                },
                title = { Text(text = "Категорії") },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = "Search"
                        )
                    }
                },
            )
        }
    ) { paddings ->
        Box(modifier = modifier
            .padding(paddings)
            .padding(Constants.HORIZONTAL_PADDING)
            .fillMaxSize()) {
            val hidedServices by viewModel.hidedServices.observeAsState(emptyList())
            LazyColumn(modifier = modifier) {
                items(prices) {
                    if (it.state == ItemState.DEFAULT) {
                        ServiceListItem(
                            onEvent = {},
                            service = it.copy(categoryName = viewModel.categoryName),
                            nextScreen = nextScreen,
                            onHided = { viewModel.onEvent(ServicesScreenEvent.Hide(it.id)) }
                        )
                        HorizontalDivider(color = Color.Gray)
                    }
                }
                item {
                    Text(
                        modifier = Modifier.padding(top = 14.dp),
                        text = "Hided",
                        style = Typography.titleMedium
                    )
                }
                items(hidedServices) {
                    if (it.state == ItemState.HIDED) {
                        ServiceListItem(
                            modifier = modifier.alpha(0.5f),
                            service = it.copy(categoryName = viewModel.categoryName),
                            nextScreen = nextScreen,
                            onEvent = {},
                            onHided = { viewModel.onEvent(ServicesScreenEvent.Hide(it.id)) }
                        )
                        HorizontalDivider(modifier = modifier.alpha(0.5f), color = Color.Gray)
                    }
                }
            }
        }
    }
}
