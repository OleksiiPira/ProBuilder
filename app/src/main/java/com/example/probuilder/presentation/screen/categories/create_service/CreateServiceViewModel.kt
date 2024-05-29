package com.example.probuilder.presentation.screen.categories.create_service

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.data.local.ServiceRepository
import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.Service
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateServiceViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ServiceRepository
) : ViewModel() {
    private var state = MutableStateFlow(CreateServiceState())
    val serviceState: StateFlow<CreateServiceState> = state

    init {
        savedStateHandle.get<String>("category")?.let { categoryStr ->
            val category = Gson().fromJson(categoryStr, Category::class.java)
            state.update { it.copy(currCategory = category, categoryName = category.name) }
        }

        savedStateHandle.get<String>("service").let { serviceStr ->
            val service = Gson().fromJson(serviceStr, Service::class.java)
            state.update { it.copy(
                name = service.name,
                unit = service.measure,
                pricePerUnit = service.pricePerUnit.toString(),
                categoryName = service.categoryName
            ) }
        }
    }

    fun onEvent(event: CreateServiceEvent) {
        when(event) {
            CreateServiceEvent.SaveItem -> {
                val state = state.value
                val item = Service(
                    id = UUID.randomUUID().toString(),
                    name = state.name,
                    categoryId = state.currCategory.id,
                    categoryName = state.categoryName,
                    pricePerUnit = state.pricePerUnit.toIntOrNull() ?: 0,
                    measure = state.unit
                )
                viewModelScope.launch { repository.upsert(item) }
            }
            is CreateServiceEvent.SetName -> state.update { it.copy(name = event.name) }
            is CreateServiceEvent.SetCategory -> state.update { it.copy(categoryName = event.category) }
            is CreateServiceEvent.SetUnit -> state.update { it.copy(unit = event.unit) }
            is CreateServiceEvent.SetPricePerUnit -> state.update { it.copy(pricePerUnit = event.pricePreUnit) }
        }
    }
}
