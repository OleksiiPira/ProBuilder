package com.example.probuilder.presentation.screen.categories.categories_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.common.Resource
import com.example.probuilder.data.local.CategoriesRepository
import com.example.probuilder.data.local.ServiceRepository
import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.Service
import com.example.probuilder.domain.use_case.CategoriesUseCase
import com.example.probuilder.domain.use_case.GetServices
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoryScreenEvent.CreateCategory
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoryScreenEvent.FavoriteCategory
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoryScreenEvent.FavoriteSelectedCategory
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoryScreenEvent.HideSelectedCategories
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoryScreenEvent.HideService
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoryScreenEvent.SelectAll
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoryScreenEvent.ShowCategory
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoryScreenEvent.UpdateCategorySelectedState
import com.example.probuilder.presentation.screen.categories.categories_screen.CategoryScreenEvent.UpdateExpandServices
import com.google.gson.Gson
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
    private val categoriesUseCase: CategoriesUseCase,
    private val categoriesRepository: CategoriesRepository,
    private val servicesRepository: ServiceRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val screenState = MutableStateFlow(CategoriesScreenState())

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _services: MutableStateFlow<List<Service>> = MutableStateFlow(emptyList())
    val services: StateFlow<List<Service>> = _services

    init {
        val categoryStr = savedStateHandle.get<String>("category")
        if (categoryStr.isNullOrBlank()) {
            showMainCategories()
            categories.value.ifEmpty { httpGetCategories() }
//            httpPopulateServices() if servicesDb.empty
        } else {
            val category = Gson().fromJson(categoryStr, Category::class.java)
            onEvent(ShowCategory(category))
        }
    }

    fun onEvent(event: CategoryScreenEvent) {
        when (event) {
            is ShowCategory -> viewModelScope.launch {
                val category = event.category

                updateServices(category.id)
                screenState.update { it.copy(hasParent = category.id != "main", currCategory = category) }
                categoriesRepository
                    .getCategoryByParentId(category.id)
                    .collect { categories -> _categories.value = categories }
            }

            is CreateCategory -> viewModelScope.launch {
                categoriesRepository.upsertCategory(event.category)
                screenState.value.hideOverlays()
            }

            is UpdateCategorySelectedState -> {
                viewModelScope.launch {
                    val category = event.category
                    val state = screenState.value
                    if (state.selectedItems.isNotEmpty() && state.itemsMode != category.state) {
                        val mode = getCurrentMode(state.itemsMode)
                        state.showError("Закінчіть зміни з $mode, щоб перейти до інших.")
                        return@launch
                    }

                    screenState.update { currentState ->
                        val newMap = currentState.selectedItems.toMutableMap().apply {
                            val removedValue = remove(category.id)
                            if (removedValue == null) {
                                put(category.id, category)
                            }
                        }
                        currentState.copy(
                            selectedItems = newMap,
                            isEditMode = newMap.isNotEmpty(),
                            itemsMode = category.state
                        )
                    }
                }
            }

            is HideSelectedCategories -> viewModelScope.launch {
                screenState.value.selectedItems.values.forEach { category ->
                    if (category.state != ItemState.HIDED) {
                        categoriesRepository.upsertCategory(category.copy(state = ItemState.HIDED))
                    } else {
                        categoriesRepository.upsertCategory(category.copy(state = ItemState.DEFAULT))
                    }
                }
                screenState.value = CategoriesScreenState()
            }

            SelectAll -> viewModelScope.launch {
                val state = screenState.value
                val selectedItems = mutableMapOf<String, Category>()
                if(state.selectAll) { _categories.value.forEach { selectedItems[it.id] = it } }
                screenState.update { it.copy(
                    selectedItems = selectedItems,
                    selectAll = !state.selectAll,
                    isEditMode = selectedItems.isNotEmpty()
                )}
            }

            is FavoriteCategory -> viewModelScope.launch {
                val category = event.category
                if (category.state == ItemState.FAVORITE) {
                    categoriesRepository.upsertCategory(category.copy(state = ItemState.DEFAULT))
                } else {
                    categoriesRepository.upsertCategory(category.copy(state = ItemState.FAVORITE))
                }
                screenState.value = CategoriesScreenState()
            }
            FavoriteSelectedCategory -> viewModelScope.launch {
                val newMap = screenState.value.selectedItems.toMutableMap()
                val selectedMode = newMap.values.first().state
                screenState.value.selectedItems.values.filter { it.state == selectedMode }
                    .forEach { category ->
                        if (category.state == ItemState.FAVORITE) {
                            categoriesRepository.upsertCategory(category.copy(state = ItemState.DEFAULT))
                        } else {
                            categoriesRepository.upsertCategory(category.copy(state = ItemState.FAVORITE))
                        }
                    }
                screenState.value = CategoriesScreenState()
            }

            is UpdateExpandServices -> screenState.update {
                it.copy(expendedServices = it.expendedServices.toMutableList().apply {
                    var isRemoved = remove(event.servicesState)
                    if (!isRemoved) add(event.servicesState)
                })
            }

            is HideService -> viewModelScope.launch {
                var service = event.service
                _services.update {
                    _services.value.toMutableList().map {
                        var newState = service.state
                        if (service.id == it.id) {
                            val newState = if (it.state == ItemState.DEFAULT) ItemState.HIDED else ItemState.DEFAULT
                        }
                        it.copy(state = newState)
                    }
                }

//                if (service.state == ItemState.HIDED) {
//                    _services.value.map { if (it.id == service.id) it.copy(state = ItemState.DEFAULT) else it }
//                } else {
//                    _services.value.map { if (it.id == service.id) it.copy(state = ItemState.HIDED) else it }
//                }
            }

            is CategoryScreenEvent.DeleteService -> viewModelScope.launch { servicesRepository.delete(event.service) }

            is CategoryScreenEvent.Back -> viewModelScope.launch {
                val parentId = screenState.value.currCategory.parentId
                categoriesRepository.getCategoryById(parentId).collectLatest { prevCategory ->
                    println("Prev cate = $prevCategory")
                    if (prevCategory != null) {
                        screenState.update { it.copy(currCategory = prevCategory) }
                        onEvent(ShowCategory(prevCategory))
                    } else {
                        screenState.update { it.copy(hasParent = false) }
                        showMainCategories()
                    }
                }
            }
        }
    }

    private fun getCurrentMode(itemState: ItemState) = mapOf(
        ItemState.HIDED to "прихованими",
        ItemState.FAVORITE to "улюбленими",
        ItemState.DEFAULT to "звичайними"
    )[itemState]

    private fun showMainCategories(){
        onEvent(ShowCategory(Category(id="main")))
    }

    private fun httpGetCategories() {
        viewModelScope.launch {
            categoriesUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data.orEmpty()
                            .flatMap { it.value }
                            .forEach { categoriesRepository.upsertCategory(it) }
                        showMainCategories()
                    }

                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                }
            }
        }
    }

    private fun httpPopulateServices() {
        viewModelScope.launch {
            getServices.invoke().collect() { result ->
                when(result) {
                    is Resource.Error -> TODO()
                    is Resource.Loading -> TODO()
                    is Resource.Success -> {
                        val servicesLists = result.data.orEmpty()
                        servicesLists.values.flatten().forEach { service ->
                            servicesRepository.upsert(service)
                        }
                    }
                }
            }
        }
    }

    private fun updateServices(categoryId: String) {
        viewModelScope.launch {
            servicesRepository.getAllByCategoryId(categoryId).collect { services ->
                _services.value = services
            }
        }
    }
}

