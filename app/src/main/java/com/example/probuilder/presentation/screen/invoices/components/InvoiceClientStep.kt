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
fun InvoiceClientStep(
    invoice: InvoiceFormData,
    viewModel: UpsertInvoiceViewModel,
    onBack: () -> Unit = {}
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        text = "Інформація про замовника",
        style = MaterialTheme.typography.headlineSmall,
    )
    TextFieldWithTitle(
        title = "Назва клієнта",
        value = invoice.clientName,
        onValueChange = { viewModel.onEvent(InvoiceFormEvent.SetClientName(it)) })
    TextFieldWithTitle(
        title = "ЄДРПОУ або ІПН",
        value = invoice.clientPersonNumber,
        onValueChange = { viewModel.onEvent(InvoiceFormEvent.SetClientPersonNumber(it)) })
    TextFieldWithTitle(
        title = "Адреса",
        value = invoice.clientAddress,
        onValueChange = { viewModel.onEvent(InvoiceFormEvent.SetClientAddress(it)) })
    TextFieldWithTitle(
        title = "Номер телефону",
        value = invoice.clientPhone,
        onValueChange = { viewModel.onEvent(InvoiceFormEvent.SetClientPhone(it)) })
    TextFieldWithTitle(
        title = "Електронна скринька",
        value = invoice.clientEmail,
        onValueChange = { viewModel.onEvent(InvoiceFormEvent.SetClientEmail(it)) })
    BackHandler { onBack() }
}
