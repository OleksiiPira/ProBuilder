package com.example.probuilder.presentation.screen.categories.create_service

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.data.local.CategoriesRepository
import com.example.probuilder.data.local.ServiceRepository
import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.Service
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateServiceViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ServiceRepository,
    private val categoryRepository: CategoriesRepository
) : ViewModel() {
    private var _screenState = MutableStateFlow(CreateServiceState())
    val screenState: StateFlow<CreateServiceState> = _screenState

    private val _selectedCategory = mutableStateOf(Category())
    val selectedCategory: State<Category> = _selectedCategory

    private val _newService = mutableStateOf(Service())
    val newService: State<Service> = _newService

    private val _allCategories = MutableStateFlow<List<Category>>(emptyList())
    val allCategories: StateFlow<List<Category>> = _allCategories.asStateFlow()

    init {
        setCurrentCategory()
        setCurrentServiceIfPresent()
        loadAllCategories()
    }

    fun onEvent(event: CreateServiceEvent) {
        when (event) {
            CreateServiceEvent.SaveService -> {
                viewModelScope.launch {
                    val newService = newService.value.copy(categoryId = selectedCategory.value.id)
                    _newService.value = newService
                    repository.upsert(newService)
                }
            }
            is CreateServiceEvent.SetServiceName -> {
                _newService.value = _newService.value.copy(name = event.name)
            }
            is CreateServiceEvent.UpdateCurrentCategory -> {
                viewModelScope.launch {
                    val newCategory = event.category
                    _selectedCategory.value = newCategory
                    _newService.value = _newService.value.copy(categoryId = newCategory.id)
                }
            }
            is CreateServiceEvent.SetMeasureUnit -> {
                _newService.value = _newService.value.copy(measureUnit = event.measureUnit)
            }
            is CreateServiceEvent.SetPricePerUnit -> {
                val price = event.pricePreUnit
                _screenState.update { it.copy(pricePerUnit = price) }
                _newService.value = _newService.value.copy(pricePerUnit = price.toIntOrNull() ?: 0)
            }

        }
    }

    private fun setCurrentCategory() {
        savedStateHandle.get<String>("category")?.takeIf { it.isNotEmpty() }?.let { categoryStr ->
            viewModelScope.launch {
                val categoryObj = Gson().fromJson(categoryStr, Category::class.java)
                categoryRepository
                    .getCategoryById(categoryObj.id)
                    .collect { category ->
                        _selectedCategory.value = category
                        _screenState.value = _screenState.value.copy(inputCategoryName = category.name)
                    }
            }
        }
    }

    private fun setCurrentServiceIfPresent() {
        savedStateHandle.get<String>("service")?.takeIf { it.isNotEmpty() }?.let { serviceStr ->
            val service = Gson().fromJson(serviceStr, Service::class.java)
            _newService.value = service
        }
    }

    private fun loadAllCategories() {
        viewModelScope.launch {
            categoryRepository.getCategories().collect { result ->
                _allCategories.value = result
            }
        }
    }
}
