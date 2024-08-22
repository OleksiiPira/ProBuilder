package com.example.probuilder.presentation.screen.categories.job_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.Job
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class JobDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private var _job = MutableStateFlow(Job())
    val job: StateFlow<Job> = _job

    private var _category = MutableStateFlow(Category())
    val category: StateFlow<Category> = _category

    init {
        savedStateHandle.get<String>("job")?.let {
            val job = Gson().fromJson(it, Job::class.java)
            _job.value = job
        }
        savedStateHandle.get<String>("category")?.let {
            _category.value = Gson().fromJson(it, Category::class.java)
        }
    }
}
