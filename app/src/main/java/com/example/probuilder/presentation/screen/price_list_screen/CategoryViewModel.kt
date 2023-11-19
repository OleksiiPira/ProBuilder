package com.example.probuilder.presentation.screen.price_list_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.Job
import javax.inject.Inject

// TODO: 50min "How to Make a Clean Architecture Cryptocurrency App"
class CategoryViewModel @Inject constructor(
    private val getPriceListViewModel: PriceListViewModel
) : ViewModel() {
    private val _state = mutableStateOf(Category())
    val state: State<Category> = _state

    public fun getCategoryById(categoryId: String) {
        _state.value = getPriceListViewModel.getPriceListByCategoryId(categoryId)
    }

    public fun getJobById(title: String): Job {
        return state.value.jobs.find { job: Job -> job.title == title } ?: Job()
    }
}