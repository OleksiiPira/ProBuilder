package com.example.probuilder.presentation.screen.categories.job_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.common.Constants
import com.example.probuilder.presentation.components.ColumnListItem
import com.example.probuilder.presentation.components.PrimaryButton
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.screen.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailsScreen(
    modifier: Modifier = Modifier,
    nextScreen: (String) -> Unit,
    viewModel: JobDetailsViewModel = hiltViewModel(),
    bottomBar: @Composable() (() -> Unit)
) {
    val service by viewModel.jobState.collectAsState()
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
            modifier = modifier.padding(it).padding(Constants.HORIZONTAL_PADDING),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                text = service.name,
                style = Typography.titleLarge
            )
            ColumnListItem(title = "Категорія", value = "ServiceDetailsScreen")
            ColumnListItem(title = "Одиниця виміру", value = service.measureUnit)
            ColumnListItem(title = "Ціна", value = service.pricePerUnit.toString())
            Spacer(modifier = Modifier.padding(bottom = 8.dp))
            PrimaryButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Додати до фактури",
                    style = Typography.labelLarge
                )
            }
            SecondaryButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Приховати",
                    style = Typography.labelLarge
                )
            }
            SecondaryButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Видалити",
                    style = Typography.labelLarge
                )
            }
        }
    }
}