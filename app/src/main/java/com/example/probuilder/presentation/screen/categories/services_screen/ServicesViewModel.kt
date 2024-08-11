package com.example.probuilder.presentation.screen.categories.services_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.data.remote.JobService
import com.example.probuilder.domain.model.Service
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServicesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val jobService: JobService,
    ) : ViewModel() {

    private val _state = MutableStateFlow(ServicesScreenState())
    val state: Flow<ServicesScreenState> = _state

    private val _jobs = jobService.jobs
    val jobs: Flow<List<Service>> = _jobs.map { list -> list.filter { it.categoryId == _state.value.currCategory.id } }

    init {
        savedStateHandle.get<String>("categoryId")?.let { id -> _state.value.currCategory.id = id }
        savedStateHandle.get<String>("categoryName")?.let { name -> _state.value.currCategory.name = name }
    }

    fun selectJob(service: Service) = _state.update { state ->
        state.copy(
            selectedServices = state.selectedServices.toMutableList().apply { if (!remove(service)) { add(service) } },
            isEditMode = true
        )
    }
    fun selectServices(services: List<Service>) = _state.update { state ->
        state.copy(selectedServices = services.toMutableList()
            .apply { if (services.size == state.selectedServices.size) { clear() }
        })
    }

    fun removeJobs(jobs: List<Service>) = viewModelScope.launch {
        jobs.forEach { jobService.delete(it) }
    }

    private fun loadAllJobs() {
//        viewModelScope.launch {
//            getServices.invoke().collect { result ->
//                when (result) {
//                    is Resource.Success -> {
//                        val data = result.data.orEmpty()
//                        _jobs.value = data
//                        _currJobs.value = _jobs.value?.get(currCatId.value).orEmpty()
//                    }
//
//                    is Resource.Error -> {}
//                    is Resource.Loading -> {}
//                }
//            }
//        }
    }

    fun updatePrices() {
//        savedStateHandle.get<String>("categoryId")?.let {
//            currCatId.value = it
//            _jobs.value?.get(currCatId.value)?.let { prices ->
//                _currJobs.value = prices
//            }
//        }
    }


}

