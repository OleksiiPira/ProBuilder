package com.example.probuilder.presentation.screen.invoices.invoice_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.probuilder.common.Constants.HORIZONTAL_PADDING
import com.example.probuilder.domain.model.Invoice
import com.example.probuilder.presentation.components.ColumnListItem
import com.example.probuilder.presentation.components.PrimaryButton
import com.example.probuilder.presentation.components.SecondaryButton
import com.example.probuilder.presentation.components.TitleWithEditButton
import com.example.probuilder.presentation.screen.invoices.components.InvoiceClientStep
import com.example.probuilder.presentation.screen.invoices.components.InvoiceInfoStep
import com.example.probuilder.presentation.screen.invoices.components.InvoicePerformerStep
import com.example.probuilder.presentation.screen.invoices.create_invoice.InvoiceFormEvent
import com.example.probuilder.presentation.screen.invoices.create_invoice.toInvoice
import com.example.probuilder.presentation.screen.invoices.invoice_details.InvoiceDetailsState.EDIT_CLIENT
import com.example.probuilder.presentation.screen.invoices.invoice_details.InvoiceDetailsState.EDIT_INVOICE
import com.example.probuilder.presentation.screen.invoices.invoice_details.InvoiceDetailsState.EDIT_PERFORMER
import com.example.probuilder.presentation.screen.invoices.invoice_details.InvoiceDetailsState.INVOICE_FULL_INFO
import com.example.probuilder.presentation.screen.ui.theme.Typography

enum class InvoiceDetailsState {
    INVOICE_FULL_INFO, EDIT_INVOICE, EDIT_PERFORMER, EDIT_CLIENT
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceDetailsScreen(
    modifier: Modifier = Modifier,
    nextScreen: (String) -> Unit,
    viewModel: InvoicesDetailsViewModel = hiltViewModel(),
    bottomBar: @Composable() (() -> Unit)
) {
    var currentForm by rememberSaveable { mutableStateOf(INVOICE_FULL_INFO) }
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
            modifier = modifier
                .padding(it).padding(HORIZONTAL_PADDING)
                .verticalScroll(rememberScrollState()),
        ) {

            when (currentForm) {
                INVOICE_FULL_INFO -> {
                    InvoiceDetailsContent(
                        onNextScreen = { currentForm = it },
                        invoice = invoice.toInvoice(invoice)
                    )
                }

                EDIT_INVOICE -> {
                    InvoiceInfoStep(
                        invoice = invoice,
                        viewModel = hiltViewModel(),
                        onBack = { currentForm = INVOICE_FULL_INFO }
                    )
                }

                EDIT_PERFORMER -> {
                    InvoicePerformerStep(
                        invoice = invoice,
                        viewModel = hiltViewModel(),
                        onBack = { currentForm = INVOICE_FULL_INFO }
                    )
                }

                EDIT_CLIENT -> {
                    InvoiceClientStep(
                        invoice = invoice,
                        viewModel = hiltViewModel(),
                        onBack = { currentForm = INVOICE_FULL_INFO }
                    )
                }

            }
            if (currentForm != INVOICE_FULL_INFO) {
                PrimaryButton(
                    text = "Зберегти",
                    onClick = {
                        currentForm = INVOICE_FULL_INFO
                        viewModel.onEvent(InvoiceFormEvent.SaveItem)
                    },
                )
                SecondaryButton(text = "Відмінити", onClick = { currentForm = INVOICE_FULL_INFO })
            }
        }
    }

}

@Composable
fun InvoiceDetailsContent(
    modifier: Modifier = Modifier,
    onNextScreen: (InvoiceDetailsState) -> Unit,
    invoice: Invoice
) {

    Spacer(modifier = Modifier.height(8.dp))
    TitleWithEditButton(title = "Інформація про фактуру") { onNextScreen(EDIT_INVOICE) }
    ColumnListItem(title = "Name", value = invoice.name)
    ColumnListItem(title = "Invoice Number", value = invoice.invoiceNumber)
    ColumnListItem(title = "Date", value = invoice.date)
    ColumnListItem(title = "Final Payment Date", value = invoice.finalPaymentDate)

    Spacer(modifier = Modifier.height(8.dp))
    TitleWithEditButton(title = "Виконавець") { onNextScreen(EDIT_PERFORMER) }
    ColumnListItem(title = "Performer Name", value = invoice.performerName)
    ColumnListItem(title = "Performer Person Number", value = invoice.performerPersonNumber)
    ColumnListItem(title = "Performer Address", value = invoice.performerAddress)
    ColumnListItem(title = "Performer Phone", value = invoice.performerPhone)
    ColumnListItem(title = "Performer Email", value = invoice.performerEmail)
    ColumnListItem(title = "Performer IBAN", value = invoice.performerIban)
    ColumnListItem(title = "Performer Payment Details", value = invoice.performerPaymentDetails)

    Spacer(modifier = Modifier.height(8.dp))
    TitleWithEditButton(title = "Клієнт") { onNextScreen(EDIT_CLIENT) }
    ColumnListItem(title = "Client Name", value = invoice.clientName)
    ColumnListItem(title = "Client Person Number", value = invoice.clientPersonNumber)
    ColumnListItem(title = "Client Address", value = invoice.clientAddress)
    ColumnListItem(title = "Client Phone", value = invoice.clientPhone)
    ColumnListItem(title = "Client Email", value = invoice.clientEmail)
}

@Preview
@Composable
fun PreviewInvoiceDetailsScreen() {
    InvoiceDetailsContent(
        modifier = Modifier.background(Color.White),
        onNextScreen = {},
        invoice = Invoice()
    )
}
