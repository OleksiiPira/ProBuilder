package com.example.probuilder.presentation.screen.invoices.create_invoice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.common.Constants.HORIZONTAL_PADDING
import com.example.probuilder.presentation.Route
import com.example.probuilder.presentation.components.PrimaryButton
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.screen.home.TabButton
import com.example.probuilder.presentation.screen.invoices.components.InvoiceClientStep
import com.example.probuilder.presentation.screen.invoices.components.InvoiceInfoStep
import com.example.probuilder.presentation.screen.invoices.components.InvoicePerformerStep
import com.example.probuilder.presentation.screen.ui.theme.Typography

enum class InvoiceUpsertStep { INFO, PERFORMER, CLIENT }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertInvoiceScreen(
    modifier: Modifier = Modifier,
    nextScreen: (String) -> Unit,
    viewModel: UpsertInvoiceViewModel = hiltViewModel(),
    bottomBar: @Composable() (() -> Unit)
) {
    val invoice by viewModel.invoiceFormData.collectAsState()
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
            modifier = modifier.padding(it)
                .padding(HORIZONTAL_PADDING)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)

        ) {
            var currentForm by rememberSaveable { mutableStateOf(InvoiceUpsertStep.INFO) }
            ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
            var isInfoUpdated by rememberSaveable { mutableStateOf(false) }
            var isPerformerUpdated by rememberSaveable { mutableStateOf(false) }
            var isClientUpdated by rememberSaveable { mutableStateOf(false) }

            Column(
                verticalArrangement = Arrangement.spacedBy((-6).dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TabButton(
                        modifier = Modifier.weight(1f),
                        text = "Фактура",
                        onClick = { currentForm = InvoiceUpsertStep.INFO },
                        isSelected = currentForm == InvoiceUpsertStep.INFO,
                        isUpdated = isInfoUpdated
                    )
                    TabButton(
                        modifier = Modifier.weight(1f),
                        text = "Виконавець",
                        onClick = { currentForm = InvoiceUpsertStep.PERFORMER },
                        isSelected = currentForm == InvoiceUpsertStep.PERFORMER,
                        isUpdated = isPerformerUpdated
                    )
                    TabButton(
                        modifier = Modifier.weight(1f),
                        text = "Замовник",
                        onClick = { currentForm = InvoiceUpsertStep.CLIENT },
                        isSelected = currentForm == InvoiceUpsertStep.CLIENT,
                        isUpdated = isClientUpdated
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .background(Color.Gray.copy(alpha = 0.5f))
                        .fillMaxWidth()
                )
            }

            when (currentForm) {
                InvoiceUpsertStep.INFO -> {
                    val goBack = {
                        isInfoUpdated = false
                        nextScreen(Route.BACK)
                    }
                    InvoiceInfoStep(
                        invoice = invoice,
                        viewModel = viewModel,
                        onBack = goBack
                    )
                    PrimaryButton(text = "Продовжити", onClick = {
                        isInfoUpdated = true
                        currentForm = InvoiceUpsertStep.PERFORMER
                    })
                    SecondaryButton(text = "Відмінити",onClick = goBack)
                }

                InvoiceUpsertStep.PERFORMER -> {
                    val goBack = {
                        isPerformerUpdated = false
                        currentForm = InvoiceUpsertStep.INFO
                    }
                    InvoicePerformerStep(
                        invoice = invoice,
                        viewModel = viewModel,
                        onBack = goBack
                    )
                    PrimaryButton(text = "Продовжити", onClick = {
                        isPerformerUpdated = true
                        currentForm = InvoiceUpsertStep.CLIENT
                    })
                    SecondaryButton(text = "Назад", onClick = goBack)
                }

                InvoiceUpsertStep.CLIENT -> {
                    val goBack = {
                        isClientUpdated = false
                        currentForm = InvoiceUpsertStep.PERFORMER
                    }
                    InvoiceClientStep(
                        invoice = invoice,
                        viewModel = viewModel,
                        onBack = goBack
                    )
                    PrimaryButton(
                        text = "Зберегти",
                        onClick = {
                            isClientUpdated = true
                            viewModel.onEvent(InvoiceFormEvent.SaveItem)
                            nextScreen(Route.INVOICES)
                        },
                    )
                    SecondaryButton(text = "Назад", onClick = goBack)
                }
            }
        }
    }
}
