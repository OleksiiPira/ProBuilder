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

    private var _jobs = MutableLiveData(emptyList<Job>())
    val jobs: LiveData<List<Job>> = _jobs

    init {
        savedStateHandle.get<String>("categoryId")?.let { id ->
            _state.value.currCategory.id = id
            viewModelScope.launch {
                jobService.fetchJobs(id, {jobsList ->
                    _jobs.value = jobsList.filter { it.categoryId == id }
                }, {})
            }
        }
        savedStateHandle.get<String>("categoryName")?.let { name -> _state.value.currCategory.name = name }
    }

    fun selectJob(job: Job) = _state.update { state ->
        state.copy(
            selectedJobs = state.selectedJobs.toMutableList().apply { if (!remove(job)) { add(job) } },
            isEditMode = true
        )
    }
    fun selectServices(jobs: List<Job>) = _state.update { state ->
        state.copy(selectedJobs = jobs.toMutableList()
            .apply { if (jobs.size == state.selectedJobs.size) { clear() }
        })
    }

    fun removeJobs(jobs: List<Job>) = viewModelScope.launch {
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

