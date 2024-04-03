package com.example.probuilder.presentation.screen.categories.categories_sub

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.common.Resource
import com.example.probuilder.domain.model.SubCategory
import com.example.probuilder.domain.use_case.GetSubCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SubCategoriesViewModel @Inject constructor(
    private val getSubCategoriesUseCase: GetSubCategories,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _subCategories = MutableLiveData<Map<String,List<SubCategory>>>(emptyMap())
    val subCategories: LiveData<Map<String,List<SubCategory>>> = _subCategories

    private val _curSubcategories = MutableLiveData<List<SubCategory>>(emptyList())
    val curSubcategories: LiveData<List<SubCategory>> = _curSubcategories
    val curCategoryName: String = savedStateHandle.get<String>("categoryName").orEmpty()

    init {
        viewModelScope.launch {
            loadCategories()
            loadSubCategories()
        }
    }

    fun loadSubCategories(){
        savedStateHandle.get<String>("categoryId")?.let { categoryId ->
            subCategories.value?.get(categoryId)?.let { subCategories ->
                _curSubcategories.value = subCategories
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            getSubCategoriesUseCase.invoke().collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        _subCategories.value = result.data.orEmpty()
                    }

                    is Resource.Error -> {
//                        _rows.value = listOf(Row("Error", "Error", result.message.orEmpty()))

                    }

                    is Resource.Loading -> {
//                        _rows.value = listOf(Row("Loading", "Loading", "Loading"))

                    }
                }
            }
        }
    }
}
