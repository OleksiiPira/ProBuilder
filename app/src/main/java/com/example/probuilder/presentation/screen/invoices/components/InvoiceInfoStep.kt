package com.example.probuilder.presentation.screen.invoices.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.probuilder.presentation.common.TextFieldWithTitle
import com.example.probuilder.presentation.screen.invoices.create_invoice.InvoiceFormEvent
import com.example.probuilder.presentation.screen.invoices.create_invoice.InvoiceFormData
import com.example.probuilder.presentation.screen.invoices.create_invoice.UpsertInvoiceViewModel

@Composable
fun InvoiceInfoStep(
    invoice: InvoiceFormData,
    viewModel: UpsertInvoiceViewModel,
    onBack: () -> Unit = {}
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        text = "Інформація про фактуру",
        style = MaterialTheme.typography.headlineSmall
    )
    TextFieldWithTitle(
        title = "Назва фактури (для легкого пошуку)",
        value = invoice.name,
        onValueChange = { viewModel.onEvent(InvoiceFormEvent.SetInvoiceFormName(it)) })
    TextFieldWithTitle(
        title = "Номер фактури",
        value = invoice.invoiceNumber,
        onValueChange = { viewModel.onEvent(InvoiceFormEvent.SetInvoiceFormNumber(it)) })
    TextFieldWithTitle(
        title = "Дата",
        value = invoice.date,
        onValueChange = { viewModel.onEvent(InvoiceFormEvent.SetDate(it)) })
    TextFieldWithTitle(
        title = "Термін сплати до",
        value = invoice.lastPaymentDate,
        onValueChange = { viewModel.onEvent(InvoiceFormEvent.SetFinalPaymentDate(it)) })
    BackHandler { onBack() }
}
