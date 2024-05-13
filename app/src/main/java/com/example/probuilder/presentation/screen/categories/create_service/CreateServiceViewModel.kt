package com.example.probuilder.presentation.screen.categories.create_service

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.probuilder.data.local.InvoiceRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CreateServiceViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: InvoiceRepositoryImpl
) : ViewModel() {
    private var createServiceState = MutableStateFlow(CreateServiceState())
    val serviceState: StateFlow<CreateServiceState> = createServiceState

    init {
//        savedStateHandle.get<String>("item")?.let {
//            val service = Gson().fromJson(it, Service::class.java)
//            createServiceState.value = service
//        }
    }

    fun onEvent(event: CreateServiceEvent) {
        when(event) {
            CreateServiceEvent.SaveItem -> TODO()
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
            is CreateServiceEvent.SetName -> createServiceState.update { it.copy(name = event.name) }
            is CreateServiceEvent.SetCategory -> createServiceState.update { it.copy(category = event.category) }
            is CreateServiceEvent.SetUnit -> createServiceState.update { it.copy(unit = event.unit) }
            is CreateServiceEvent.SetPricePerUnit -> createServiceState.update { it.copy(pricePerUnit = event.pricePreUnit) }
        }
    }
}
