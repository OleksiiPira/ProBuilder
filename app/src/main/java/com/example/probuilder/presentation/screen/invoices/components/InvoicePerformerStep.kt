package com.example.probuilder.presentation.screen.invoices.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.probuilder.presentation.components.TextFieldWithTitle
import com.example.probuilder.presentation.screen.invoices.create_invoice.InvoiceFormEvent
import com.example.probuilder.presentation.screen.invoices.create_invoice.InvoiceFormData
import com.example.probuilder.presentation.screen.invoices.create_invoice.UpsertInvoiceViewModel

@Composable
fun InvoicePerformerStep(
    invoice: InvoiceFormData,
    viewModel: UpsertInvoiceViewModel,
    onBack: () -> Unit = {}
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        text = "Інформація про виконавця",
        style = MaterialTheme.typography.headlineSmall
    )
    TextFieldWithTitle(
        title = "Виконавець",
        value = invoice.performerName,
        onValueChange = { viewModel.onEvent(InvoiceFormEvent.SetPerformerName(it)) })
    TextFieldWithTitle(
        title = "ЄДРПОУ або ІПН",
        value = invoice.performerPersonNumber,
        onValueChange = { viewModel.onEvent(InvoiceFormEvent.SetPerformerPersonNumber(it)) })
    TextFieldWithTitle(
        title = "Банкіський рахунок",
        value = invoice.performerIban,
        onValueChange = { viewModel.onEvent(InvoiceFormEvent.SetPerformerIban(it)) })
    TextFieldWithTitle(
        title = "Адреса",
        value = invoice.performerAddress,
        onValueChange = { viewModel.onEvent(InvoiceFormEvent.SetPerformerAddress(it)) })
    TextFieldWithTitle(
        title = "Номер телефону",
        value = invoice.performerPhone,
        onValueChange = { viewModel.onEvent(InvoiceFormEvent.SetPerformerPhone(it)) })
    TextFieldWithTitle(
        title = "Електронна скринька",
        value = invoice.performerEmail,
        onValueChange = { viewModel.onEvent(InvoiceFormEvent.SetPerformerEmail(it)) })
    BackHandler { onBack() }
}
