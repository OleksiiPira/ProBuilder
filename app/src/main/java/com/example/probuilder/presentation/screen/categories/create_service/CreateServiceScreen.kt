package com.example.probuilder.presentation.screen.categories.create_service

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.common.Constants
import com.example.probuilder.presentation.common.PrimaryButton
import com.example.probuilder.presentation.common.SecondaryButton
import com.example.probuilder.presentation.common.TextFieldWithTitle
import com.example.probuilder.presentation.screen.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateServiceScreen(
    modifier: Modifier = Modifier,
    nextScreen: (String) -> Unit,
    viewModel: CreateServiceViewModel = hiltViewModel(),
    bottomBar: @Composable() (() -> Unit)
) {
    Scaffold(
        bottomBar = bottomBar,
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
    ) {

    Column(
        Modifier.padding(it).padding(Constants.HORIZONTAL_PADDING),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        ) {
        val createServiceState by viewModel.serviceState.collectAsState()
        TextFieldWithTitle(
            title = "Назва послуги",
            value = createServiceState.name,
            onValueChange = { viewModel.onEvent(CreateServiceEvent.SetName(it))}
        )
        TextFieldWithTitle(
            title = "Тип роботи",
            value = createServiceState.category,
            onValueChange = { viewModel.onEvent(CreateServiceEvent.SetCategory(it))}
        )
        TextFieldWithTitle(
            title = "Одиниця виміру",
            value = createServiceState.unit,
            onValueChange = { viewModel.onEvent(CreateServiceEvent.SetUnit(it))}
        )
        TextFieldWithTitle(
            title = "Одиниця виміру",
            value = createServiceState.pricePerUnit,
            onValueChange = { viewModel.onEvent(CreateServiceEvent.SetPricePerUnit(it))}
        )
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(onClick = { /*TODO*/ }) {
            Text(text = "Зберегти", style = Typography.labelLarge)
        }
        SecondaryButton(onClick = { /*TODO*/ }) {
            Text(text = "Відмінити", style = Typography.labelLarge)
        }
    }
    }
}
