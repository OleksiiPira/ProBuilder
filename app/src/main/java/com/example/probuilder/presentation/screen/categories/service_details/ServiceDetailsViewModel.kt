﻿package com.example.probuilder.presentation.screen.categories.service_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.probuilder.data.local.InvoiceRepositoryImpl
import com.example.probuilder.domain.model.Service
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class ServiceDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: InvoiceRepositoryImpl
) : ViewModel() {
    private var _serviceState = MutableStateFlow(Service())
    val serviceState: StateFlow<Service> = _serviceState

    init {
        savedStateHandle.get<String>("item")?.let {
            val service = Gson().fromJson(it, Service::class.java)
            _serviceState.value = service
        }
    }

//    fun onEvent(event: AddItemInvoiceEvent) {
//        when (event) {
//            AddItemInvoiceEvent.SaveItem -> {
//                val state = invoiceItemState.value
//                val name = state.name
//                val quantity = state.quantity.toIntOrNull() ?: 0
//                val pricePerUnit = state.pricePerUnit.toDoubleOrNull() ?: 0.0
//                val unit = state.unit
//                val invoiceId = state.invoice.id
//                val id = state.id
//                if (name.isBlank() || quantity < 0 || pricePerUnit < 1 || unit.isEmpty() || invoiceId.isBlank()) {
//                    return
//                }
//
//                val item = InvoiceItem(
//                    id = id,
//                    name = name,
//                    invoiceId = invoiceId,
//                    quantity = quantity,
//                    unit = unit,
//                    pricePerUnit = pricePerUnit,
//                    totalPrice = quantity * pricePerUnit
//                )
//                viewModelScope.launch { repository.upsertInvoiceItem(item) }
//            }
//
//            is AddItemInvoiceEvent.SetName -> _invoiceItemState.update {
//                it.copy(name = event.name)
//            }
//
//            is AddItemInvoiceEvent.SetPricePerUnit -> _invoiceItemState.update {
//                it.copy(pricePerUnit = event.pricePreUnit.toString())
//            }
//
//            is AddItemInvoiceEvent.SetQuantity -> _invoiceItemState.update {
//                it.copy(quantity = event.quantity.toString())
//            }
//
//            is AddItemInvoiceEvent.SetUnit -> _invoiceItemState.update {
//                it.copy(unit = event.unit)
//            }
//
//            is AddItemInvoiceEvent.SetInvoice -> _invoiceItemState.update {
//                it.copy(invoice = event.invoice)
//            }
//        }
//    }
}
