package com.example.probuilder.presentation.screen.invoices.invoices_screen

import com.example.probuilder.domain.model.Invoice


sealed class InvoicesScreenEvent {
    data class OpenDetails(val jobId: String) : InvoicesScreenEvent()
    data class Hide(val jobId: String) : InvoicesScreenEvent()
    data class Delete(val invoice: Invoice) : InvoicesScreenEvent()
}
