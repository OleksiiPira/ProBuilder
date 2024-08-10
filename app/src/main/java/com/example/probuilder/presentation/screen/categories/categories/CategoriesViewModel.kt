package com.example.probuilder.presentation.screen.categories.categories

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.common.Resource
import com.example.probuilder.data.remote.CategoryService
import com.example.probuilder.data.remote.JobService
import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.use_case.CategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoriesUseCase: CategoriesUseCase,
    private val categoryService: CategoryService,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val screenState = MutableStateFlow(CategoriesScreenState())

    private val _categories = categoryService.categories.map { list -> list.sortedBy { it.name } }
    val categories: Flow<List<Category>> = _categories

    //  CATEGORIES
    fun selectCategory(category: Category) = screenState.update { state ->
        var newList = state.selectedCategories.toMutableList()
        if (!newList.remove(category)) newList.add(category)
        state.copy(selectedCategories = newList, isEditMode = newList.size > 0)
    }

    fun selectAllCategories(categories: List<Category>) = screenState.update { state ->
        val newList = categories.toMutableList()
        if (categories.size == screenState.value.selectedCategories.size) { newList.clear() }
        state.copy(selectedCategories = newList, isEditMode = newList.size > 0)
    }

    fun removeCategory(category: Category) = viewModelScope.launch {
        categoryService.delete(category.id)
    }
    fun removeCategories(categories: List<Category>) = viewModelScope.launch {
        categories.forEach { category -> categoryService.delete(category.id) }
    }


    private fun httpGetCategories() {
        viewModelScope.launch {
            categoriesUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data.orEmpty()
                            .flatMap { it.value }
//                            .forEach { categoriesRepository.upsertCategory(it) }
                    }

                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                }
            }
        }
    }

    private fun httpPopulateServices() {
        viewModelScope.launch {
//            getServices.invoke().collect() { result ->
//                println(result)
//                val servicesLists = result.data.orEmpty()
//                servicesLists.values.flatten().forEach { service ->
//                    servicesRepository.upsert(service)
//                }

//                when(result) {
//                    is Resource.Error -> TODO()
//                    is Resource.Loading -> TODO()
//                    is Resource.Success -> {
//                        val servicesLists = result.data.orEmpty()
//                        servicesLists.values.flatten().forEach { service ->
//                            servicesRepository.upsert(service)
//                        }
//                    }
//                }
//            }
        }
    }

    private fun updateServices(categoryId: String) {
        viewModelScope.launch {
//            servicesRepository.getAllByCategoryId(categoryId).collect { services ->
//                _services.value = services
//            }
        }
    }
}


