package com.example.probuilder.presentation.screen.categories.categories_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.common.Resource
import com.example.probuilder.data.local.CategoriesRepository
import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.Service
import com.example.probuilder.domain.use_case.GetServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getServices: GetServices,
    private val categoriesRepository: CategoriesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val categoriesScreenState = MutableStateFlow(CategoriesScreenState())

    private val _categories =
        MutableStateFlow<List<Category>>(emptyList())  // Correct type initialization
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _services: MutableStateFlow<List<Service>> = MutableStateFlow(emptyList())
    val services: StateFlow<List<Service>> = _services

    init {
        showMainCategories()
    }

    fun onEvent(event: CategoryScreenEvent) {
        when (event) {
            is CategoryScreenEvent.LoadAllCategories -> TODO()
            is CategoryScreenEvent.ShowCreateCategory -> TODO()
            is CategoryScreenEvent.ShowCategory -> viewModelScope.launch {
                val category = event.category
                loadAllJobs(category.id)

                categoriesScreenState.update { it.copy(
                    hasParent = category.id != "main",
                    currCategory = category
                ) }

                categoriesRepository.getCategoryByParentId(category.id).collect { categories ->
                    _categories.value = categories
                }
            }

            is CategoryScreenEvent.CreateCategory -> viewModelScope.launch {
                categoriesRepository.upsertCategory(
                    event.category
                )
            }

            is CategoryScreenEvent.UpdateCategorySelectedState -> {
                viewModelScope.launch {
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

            is CategoryScreenEvent.HideService -> viewModelScope.launch {
                val service = event.service
                if (service.state == ItemState.HIDED) {
                    _services.value.map { if (it.id == service.id) it.copy(state = ItemState.DEFAULT) else it }
                } else {
                    _services.value.map { if (it.id == service.id) it.copy(state = ItemState.HIDED) else it }
                }
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
                categoriesScreenState.value.selectedItems.values.filter { it.state == selectedMode }
                    .forEach { category ->
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
            is CategoryScreenEvent.Back -> viewModelScope.launch {
                val parentId = categoriesScreenState.value.currCategory.parentId
                categoriesRepository.getCategoryById(parentId).collectLatest { prevCategory ->
                    println("Prev cate = $prevCategory")
                    if (prevCategory != null) {
                        categoriesScreenState.update { it.copy(currCategory = prevCategory) }
                        onEvent(CategoryScreenEvent.ShowCategory(prevCategory))
                    } else {
                        categoriesScreenState.update { it.copy(hasParent = false) }
                        showMainCategories()
                    }
                }
            }
        }
    }

    private fun showMainCategories(){
        onEvent(CategoryScreenEvent.ShowCategory(Category(id="main")))
    }

    private fun loadAllJobs(categoryId: String) {
        viewModelScope.launch {
            getServices.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val data = result.data.orEmpty()
                        val services1 = data[categoryId] ?: emptyList()
                        if (services1.isNotEmpty()) {
                            _services.value = services1
                        }
                    }
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                }
            }
        }
    }
}

