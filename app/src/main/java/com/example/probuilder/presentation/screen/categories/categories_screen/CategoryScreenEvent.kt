package com.example.probuilder.presentation.screen.categories.categories_screen

import com.example.probuilder.domain.model.Category

sealed class CategoryScreenEvent {
    data object LoadAllCategories: CategoryScreenEvent()
    data object ShowCreateCategory: CategoryScreenEvent()
    data class ShowCategory(val id: String): CategoryScreenEvent()
    data class CreateCategory(val category: Category): CategoryScreenEvent()

    data object SelectAll: CategoryScreenEvent()
    data class UpdateCategorySelectedState(val category: Category): CategoryScreenEvent()

    data class HideCategory(val category: Category): CategoryScreenEvent()
    data object HideSelectedCategories: CategoryScreenEvent()

    data class FavoriteCategory(val category: Category): CategoryScreenEvent()
    data object FavoriteSelectedCategory: CategoryScreenEvent()

    data class ShowError(val message: String): CategoryScreenEvent()
    data object HideError: CategoryScreenEvent()
}
