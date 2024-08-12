package com.example.probuilder.presentation.screen.categories.job_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.probuilder.data.local.InvoiceRepositoryImpl
import com.example.probuilder.domain.model.Job
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class JobDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: InvoiceRepositoryImpl
) : ViewModel() {
    private var _jobState = MutableStateFlow(Job())
    val jobState: StateFlow<Job> = _jobState

    init {
        savedStateHandle.get<String>("item")?.let {
            val job = Gson().fromJson(it, Job::class.java)
            _jobState.value = job
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
