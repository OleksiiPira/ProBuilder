package com.example.probuilder.presentation.screen.invoices.invoices_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.common.Constants.HORIZONTAL_PADDING
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.screen.home.TabButton
import com.example.probuilder.presentation.screen.invoices.invoices_screen.components.InvoiceListItem
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoicesScreen(
    modifier: Modifier = Modifier,
    nextScreen: (String) -> Unit,
    viewModel: InvoicesViewModel = hiltViewModel(),
    bottomBar: @Composable() (() -> Unit)
) {
// TODO
//        Button(onClick = {
//            nextScreen(
//                Route.CREATE_INVOICE.replace(
//                    "{invoice}",
//                    Gson().toJson(Invoice())
//                )
//            )
//        }) {
//            Text(text = "Create Invoice")
//        }

    Scaffold(
        bottomBar = bottomBar,
        floatingActionButton = {
            FloatingActionButton(
                modifier = modifier.padding(16.dp),
                containerColor = Color(0xFFF2F2F2),
                contentColor = MaterialTheme.colorScheme.primary,
                onClick = { nextScreen(Route.CREATE_INVOICE) },
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
                            tint = Color(0xFFEEEEF2),
                            contentDescription = "Search"
                        )
                    }
                },
            )
        }
    ) {
        Box(
            modifier = modifier
                .padding(it)
                .padding(HORIZONTAL_PADDING)
                .fillMaxSize()
        ) {
            Column {
                var selectedIndex by remember { mutableIntStateOf(0) }
                Column(verticalArrangement = Arrangement.spacedBy((-6).dp)) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TabButton(
                            modifier = Modifier.weight(1f),
                            text = "Активні",
                            onClick = { selectedIndex = 0 }, // Update selected index
                            isSelected = selectedIndex == 0
                        )
                        TabButton(
                            modifier = Modifier.weight(1f),
                            text = "Приховані",
                            onClick = { selectedIndex = 1 }, // Update selected index
                            isSelected = selectedIndex == 1
                        )
                    }
                    HorizontalDivider(color = Color(Color.Gray.value).copy(alpha = 0.4f))
                }

                val invoices by viewModel.invoices.collectAsState(initial = listOf())
                LazyColumn {
                    items(invoices.filter { it.isHided == (selectedIndex == 1) }) {
                        InvoiceListItem(
                            onItemClick = {
                                nextScreen(
                                    Route.INVOICE_DETAILS
                                        .replace(
                                            "{invoice}", Gson().toJson(it)
                                        )
                                )
                            },
                            onHide = { viewModel.onEvent(InvoicesScreenEvent.Hide(it.id)) },
                            onDelete = { viewModel.onEvent(InvoicesScreenEvent.Delete(it)) },
                            onEdit = {
                                nextScreen(
                                    Route.CREATE_INVOICE.replace(
                                        "{invoice}",
                                        Gson().toJson(it)
                                    )
                                )
                            },
                            name = it.name,
                            finalDate = it.finalPaymentDate,
                            hidedMode = selectedIndex == 1
                        )
                    }
                }
            }
        }
    }
}
