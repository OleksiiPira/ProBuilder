package com.example.probuilder.presentation.screen.price_detail_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.probuilder.domain.model.Category
import com.example.probuilder.presentation.screen.price_list_screen.CategoryViewModel
import javax.inject.Inject

// TODO: 50min "How to Make a Clean Architecture Cryptocurrency App"
class JobDetailsViewModel @Inject constructor(
    private val getPriceListViewModel: CategoryViewModel

) : ViewModel() {
    private val _state = mutableStateOf(Category())
    val state: State<Category> = _state

    private fun getJobById(title: String) {
        getPriceListViewModel.getJobById(title)
    }
}