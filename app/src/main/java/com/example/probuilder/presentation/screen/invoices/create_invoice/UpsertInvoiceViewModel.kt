package com.example.probuilder.presentation.screen.invoices.create_invoice

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.data.local.InvoiceRepositoryImpl
import com.example.probuilder.domain.model.Invoice
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpsertInvoiceViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: InvoiceRepositoryImpl
) : ViewModel() {
    private var _invoiceFormData = MutableStateFlow(InvoiceFormData())
    val invoiceFormData: StateFlow<InvoiceFormData> = _invoiceFormData

    init {
        val invoiceStr  = savedStateHandle.get<String>("invoice")
        if (invoiceStr.isNullOrBlank()) {
            _invoiceFormData.value = InvoiceFormData()
        } else {
            val invoice = Gson().fromJson(invoiceStr, Invoice::class.java)
            _invoiceFormData.value = _invoiceFormData.value.toInvoiceState(invoice)
        }

    }

    fun onEvent(event: InvoiceFormEvent) {
        when (event) {
            InvoiceFormEvent.SaveItem -> {
                val state = invoiceFormData.value
                val invoice = state.toInvoice(invoiceFormData.value)
                viewModelScope.launch { repository.upsertInvoice(invoice = invoice)}
            }

            is InvoiceFormEvent.SetDate -> _invoiceFormData.update { it.copy(date = event.date) }
            is InvoiceFormEvent.SetFinalPaymentDate -> _invoiceFormData.update { it.copy(lastPaymentDate = event.date) }
            is InvoiceFormEvent.SetInvoiceFormNumber -> _invoiceFormData.update { it.copy(invoiceNumber = event.number) }

            is InvoiceFormEvent.SetPerformerAddress -> _invoiceFormData.update { it.copy(performerAddress = event.address) }
            is InvoiceFormEvent.SetPerformerEmail -> _invoiceFormData.update { it.copy(performerEmail = event.email) }
            is InvoiceFormEvent.SetPerformerIban -> _invoiceFormData.update { it.copy(performerIban = event.iban) }
            is InvoiceFormEvent.SetPerformerName -> _invoiceFormData.update { it.copy(performerName = event.name) }
            is InvoiceFormEvent.SetPerformerPersonNumber -> _invoiceFormData.update { it.copy(performerPersonNumber = event.personNumber) }
            is InvoiceFormEvent.SetPerformerPhone -> _invoiceFormData.update { it.copy(performerPhone = event.phone) }

            is InvoiceFormEvent.SetClientAddress -> _invoiceFormData.update { it.copy(clientAddress = event.address) }
            is InvoiceFormEvent.SetClientEmail -> _invoiceFormData.update { it.copy(clientEmail = event.email) }
            is InvoiceFormEvent.SetClientName -> _invoiceFormData.update { it.copy(clientName = event.name) }
            is InvoiceFormEvent.SetClientPersonNumber -> _invoiceFormData.update { it.copy(clientPersonNumber = event.personNumber) }
            is InvoiceFormEvent.SetClientPhone -> _invoiceFormData.update { it.copy(clientPhone = event.phone) }
            is InvoiceFormEvent.SetInvoiceFormName -> _invoiceFormData.update { it.copy(name = event.name) }
        }
    }
}
