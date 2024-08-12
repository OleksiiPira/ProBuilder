package com.example.probuilder.presentation.screen.categories.create_service

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.data.remote.CategoryService
import com.example.probuilder.data.remote.JobService
import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.Job
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateServiceViewModel @Inject constructor(
    private val jobService: JobService,
    private val categoryService: CategoryService,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private var _state = MutableStateFlow(CreateServiceState())
    val state: StateFlow<CreateServiceState> = _state

    private val _selectedCategory = mutableStateOf(getCurrCategory())
    val selectedCategory: State<Category> = _selectedCategory

    private val _newService = mutableStateOf(getSelectedJob())
    val newJob: State<Job> = _newService

    private val _allCategories = categoryService.categories
    val allCategories: Flow<List<Category>> = _allCategories

    fun saveService() = viewModelScope.launch {
        jobService.save(newJob.value.copy(categoryId = selectedCategory.value.id))
    }

    fun updateCurrentCategory(category: Category) = viewModelScope.launch {
        _selectedCategory.value.id = category.id
    }

    fun removeJob(job: Job) = viewModelScope.launch {
        jobService.delete(job)
    }

    fun onEvent(event: CreateServiceEvent) {
        when (event) {
            is CreateServiceEvent.SetServiceName -> {
                _newService.value = _newService.value.copy(name = event.name)
            }
            is CreateServiceEvent.SetMeasureUnit -> {
                _newService.value = _newService.value.copy(measureUnit = event.measureUnit)
            }
            is CreateServiceEvent.SetPricePerUnit -> {
                val price = event.pricePreUnit
                _state.update { it.copy(pricePerUnit = price) }
                _newService.value = _newService.value.copy(pricePerUnit = price.toIntOrNull() ?: 0)
            }

        }
    }

    private fun getCurrCategory(): Category {
        val categoryStr = savedStateHandle.get<String>("category")
        var category = Category()
        if (!categoryStr.isNullOrEmpty()) category = Gson().fromJson(categoryStr, Category::class.java)
        return category
    }

    private fun getSelectedJob(): Job  {
        val service = savedStateHandle.get<String>("service")
        if (service.isNullOrEmpty()) return Job()
        return Gson().fromJson(service, Job::class.java)
    }
}
