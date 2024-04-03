package com.example.probuilder.presentation.screen.invoices.invoices_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.data.local.InvoiceRepositoryImpl
import com.example.probuilder.domain.model.Invoice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoicesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: InvoiceRepositoryImpl
) : ViewModel() {
    val invoices: StateFlow<List<Invoice>> =
        repository.getInvoices().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val invoice: Invoice = Invoice()

    fun onEvent(event: InvoicesScreenEvent) {
        when (event) {
            is InvoicesScreenEvent.Hide -> {
                viewModelScope.launch {
                    val currentInvoices = invoices.value
                    currentInvoices.find { it.id == event.jobId }?.let { invoice ->
                        repository.upsertInvoice(invoice.copy(isHided = !invoice.isHided))
                    }
                }
            }
            is InvoicesScreenEvent.OpenDetails -> TODO()
            is InvoicesScreenEvent.Delete -> viewModelScope.launch { repository.deleteById(event.invoice) }
        }
    }
}
