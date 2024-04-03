package com.example.probuilder.presentation.screen.categories.services_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.common.Resource
import com.example.probuilder.domain.model.Service
import com.example.probuilder.domain.use_case.GetServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServicesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getServices: GetServices
) : ViewModel() {
    private val currCatId = mutableStateOf("")
    private val _jobs = MutableLiveData<Map<String, List<Service>>>(emptyMap())

    private val _currJobs = MutableLiveData<List<Service>>(emptyList())
    val currJobs: LiveData<List<Service>> = _currJobs
    val categoryName = savedStateHandle.get<String>("categoryName") ?: "Prices view model categoryName not found"
    val subCategoryName = savedStateHandle.get<String>("subCategoryName") ?: "Prices view model subCategoryName not found"

    private val _hidedServices = MutableLiveData<List<Service>>(emptyList())
    val hidedServices: LiveData<List<Service>> = _hidedServices
    init {
        loadAllJobs()

        savedStateHandle.get<String>("categoryId")?.let { categoryId ->
            currCatId.value = categoryId
            _jobs.value?.get(currCatId.value)?.let { services ->
                services.map { it.copy(
                    categoryName = categoryName,
                    subCategoryName = subCategoryName
                ) }
                _currJobs.value = services
            }
        }
    }

    fun onEvent(event: ServicesScreenEvent) {
        when (event) {
            is ServicesScreenEvent.Hide -> {
                _currJobs.value = _currJobs.value.orEmpty()
                    .map {
                        (if (it.id == event.jobId) {
                            _hidedServices.value = (_hidedServices.value ?: emptyList()) + it
                            it.copy(isHaded = !it.isHaded)
                        } else it) as Service
                    }
            }
            is ServicesScreenEvent.OpenDetails -> TODO()
        }

    }

    private fun loadAllJobs() {
        viewModelScope.launch {
            getServices.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val data = result.data.orEmpty()
                        _jobs.value = data
                        _currJobs.value = _jobs.value?.get(currCatId.value).orEmpty()
                        println()
                    }

                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                }
            }
        }
    }

    fun updatePrices() {
        savedStateHandle.get<String>("categoryId")?.let {
            currCatId.value = it
            _jobs.value?.get(currCatId.value)?.let { prices ->
                _currJobs.value = prices
            }
        }
    }


}

