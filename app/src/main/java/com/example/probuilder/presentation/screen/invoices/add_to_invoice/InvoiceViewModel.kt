package com.example.probuilder.presentation.screen.invoices.add_to_invoice

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.data.local.InvoiceRepositoryImpl
import com.example.probuilder.domain.model.Invoice
import com.example.probuilder.domain.model.InvoiceItem
import com.example.probuilder.domain.model.Service
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoiceViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: InvoiceRepositoryImpl
) : ViewModel() {
    private var _invoiceItemState = MutableStateFlow(InvoiceItemState())
    val invoiceItemState: StateFlow<InvoiceItemState> = _invoiceItemState
    val invoices: Flow<List<Invoice>> = repository.getInvoices()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = listOf()
            )

    init {
        savedStateHandle.get<String>("item")?.let {
            val service = Gson().fromJson(it, Service::class.java)
            _invoiceItemState.value.let {
                _invoiceItemState.update {
                    it.copy(
                        id = service.id,
                        name = service.name,
                        unit = service.measure,
                        pricePerUnit = service.pricePerUnit.toString()
                    )
                }
            }
        }
    }

    fun onEvent(event: AddItemInvoiceEvent) {
        when (event) {
            AddItemInvoiceEvent.SaveItem -> {
                val state = invoiceItemState.value
                val name = state.name
                val quantity = state.quantity.toIntOrNull() ?: 0
                val pricePerUnit = state.pricePerUnit.toDoubleOrNull() ?: 0.0
                val unit = state.unit
                val invoiceId = state.invoice.id
                val id = state.id
                if (name.isBlank() || quantity < 0 || pricePerUnit < 1 || unit.isEmpty() || invoiceId.isBlank()) {
                    return
                }
                val item = InvoiceItem(
                    id = id,
                    name = name,
                    invoiceId = invoiceId,
                    quantity = quantity,
                    unit = unit,
                    pricePerUnit = pricePerUnit,
                    totalPrice = quantity * pricePerUnit
                )
                viewModelScope.launch { repository.upsertInvoiceItem(item) }
            }
            is AddItemInvoiceEvent.SetName -> _invoiceItemState.update { it.copy(name = event.name) }
            is AddItemInvoiceEvent.SetPricePerUnit -> _invoiceItemState.update { it.copy(pricePerUnit = event.pricePreUnit.toString()) }
            is AddItemInvoiceEvent.SetQuantity -> _invoiceItemState.update { it.copy(quantity = event.quantity.toString()) }
            is AddItemInvoiceEvent.SetUnit -> _invoiceItemState.update { it.copy(unit = event.unit) }
            is AddItemInvoiceEvent.SetInvoice -> _invoiceItemState.update { it.copy(invoice = event.invoice) }
        }
    }
}
