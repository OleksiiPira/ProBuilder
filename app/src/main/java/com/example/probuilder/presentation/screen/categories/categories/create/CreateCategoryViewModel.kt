package com.example.probuilder.presentation.screen.categories.categories.create

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.data.remote.CategoryService
import com.example.probuilder.domain.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlinx.coroutines.launch
import java.util.UUID

@HiltViewModel
class CreateCategoryViewModel @Inject constructor(
    private val categoryService: CategoryService,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var parentId = savedStateHandle.get<String>("parentId").orEmpty()

    private val _categories = categoryService.categories
    val categories: Flow<List<Category>> = _categories

    fun createCategory(name: String = "TestCategory") {
        viewModelScope.launch {
            categoryService.save(Category(
                id = UUID.randomUUID().toString(),
                name = name,
                parentId = parentId
            ))
        }
    }

}