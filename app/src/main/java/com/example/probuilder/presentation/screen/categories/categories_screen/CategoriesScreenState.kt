package com.example.probuilder.presentation.screen.categories.categories_screen

import com.example.probuilder.domain.model.Category

enum class CategoryScreenMode { HIDE_OVERLAY, SHOW_ERROR }

data class CategoriesScreenState(
    val currCategory: Category = Category(id="main"),
    val screenMode: CategoryScreenMode = CategoryScreenMode.HIDE_OVERLAY,
    val isEditMode: Boolean = false,
    val isOverlayShown: Boolean = false,
    val itemsMode: ItemState = ItemState.DEFAULT,
    val selectedItems: Map<String, Category> = mapOf(),
    val selectAll: Boolean = true,
    val errorMessage: String = "",
    val hasParent: Boolean = false
) {
    fun showError(errorMessage: String): CategoriesScreenState {
        return this.copy(screenMode = CategoryScreenMode.SHOW_ERROR, isOverlayShown = true, errorMessage = errorMessage)
    }

    fun hideOverlays(): CategoriesScreenState {
        return this.copy(screenMode = CategoryScreenMode.HIDE_OVERLAY, isOverlayShown = false, errorMessage = "")
    }
}
