package com.example.probuilder.presentation.screen.categories.categories_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.common.Resource
import com.example.probuilder.data.local.CategoriesRepository
import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.use_case.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val categoriesRepository: CategoriesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _categories = categoriesRepository.getCategories()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val categories: StateFlow<List<Category>> = _categories

    val categoriesScreenState = MutableStateFlow(CategoriesScreenState())


    init {
        viewModelScope.launch { loadRows() }
    }

    fun onEvent(event: CategoryScreenEvent) {
        when (event) {
            is CategoryScreenEvent.LoadAllCategories ->  TODO()
            CategoryScreenEvent.ShowCreateCategory -> TODO()
            is CategoryScreenEvent.CreateCategory -> viewModelScope.launch { categoriesRepository.upsertCategory(event.category) }

            is CategoryScreenEvent.UpdateCategorySelectedState -> { viewModelScope.launch {
                    val category = event.category
                    val categoriesState = categoriesScreenState.value
                    if (categoriesState.selectedItems.isNotEmpty() && categoriesState.itemsMode != category.state) {
                        val mode = when (categoriesState.itemsMode) {
                            ItemState.HIDED -> "прихованими"
                            ItemState.FAVORITE -> "улюбленими"
                            ItemState.DEFAULT -> "звичайними"
                        }
                        onEvent(CategoryScreenEvent.ShowError("Закінчіть зміни з $mode, щоб перейти до інших."))
                        return@launch
                    }
                    categoriesScreenState.update { currentState ->
                        val newMap = currentState.selectedItems.toMutableMap().apply {
                            if (!containsKey(category.id)) {
                                put(category.id, category)
                            } else {
                                remove(category.id)
                            }
                        }
                        currentState.copy(
                            selectedItems = newMap,
                            isSelectingMode = newMap.isNotEmpty(),
                            itemsMode = category.state
                        )
                    }
                }

            }

            is CategoryScreenEvent.HideCategory -> viewModelScope.launch {
                val category = event.category
                if (category.state == ItemState.HIDED) {
                    categoriesRepository.upsertCategory(category.copy(state = ItemState.DEFAULT))
                } else {
                    categoriesRepository.upsertCategory(category.copy(state = ItemState.HIDED))
                }
                categoriesScreenState.value = CategoriesScreenState()
            }

            is CategoryScreenEvent.HideSelectedCategories -> viewModelScope.launch {
                categoriesScreenState.value.selectedItems.values.forEach { category ->
                    if (category.state != ItemState.HIDED) {
                        categoriesRepository.upsertCategory(category.copy(state = ItemState.HIDED))
                    } else {
                        categoriesRepository.upsertCategory(category.copy(state = ItemState.DEFAULT))
                    }
                }
                categoriesScreenState.value = CategoriesScreenState()
            }

            CategoryScreenEvent.SelectAll -> viewModelScope.launch {
                val newMap = categoriesScreenState.value.selectedItems.toMutableMap()
                val selectedMode = newMap.values.first().state
                _categories.value.filter { it.state == selectedMode }.forEach { category ->
                    if (!newMap.containsKey(category.id)) {
                        newMap[category.id] = category
                    }
                }
                categoriesScreenState.value =
                    CategoriesScreenState(isSelectingMode = true, selectedItems = newMap)
            }

            is CategoryScreenEvent.FavoriteCategory -> viewModelScope.launch {
                val category = event.category
                if (category.state == ItemState.FAVORITE) {
                    categoriesRepository.upsertCategory(category.copy(state = ItemState.DEFAULT))
                } else {
                    categoriesRepository.upsertCategory(category.copy(state = ItemState.FAVORITE))
                }
                categoriesScreenState.value = CategoriesScreenState()
            }

            CategoryScreenEvent.FavoriteSelectedCategory -> viewModelScope.launch {
                val newMap = categoriesScreenState.value.selectedItems.toMutableMap()
                val selectedMode = newMap.values.first().state
                categoriesScreenState.value.selectedItems.values.filter { it.state == selectedMode }.forEach { category ->
                    if (category.state == ItemState.FAVORITE) {
                        categoriesRepository.upsertCategory(category.copy(state = ItemState.DEFAULT))
                    } else {
                        categoriesRepository.upsertCategory(category.copy(state = ItemState.FAVORITE))
                    }
                }
                categoriesScreenState.value = CategoriesScreenState()
            }

            is CategoryScreenEvent.ShowError -> categoriesScreenState.update { it.copy(errorMessage = event.message) }
            CategoryScreenEvent.HideError -> categoriesScreenState.update { it.copy(errorMessage = "") }
        }

    }

    private fun loadRows() {
        viewModelScope.launch {
            getCategoriesUseCase.invoke().collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        if (_categories.value.size < 5)
                            result.data.let {
                                it?.forEach { category ->
                                    categoriesRepository.upsertCategory(
                                        category
                                    )
                                }
                            }
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

