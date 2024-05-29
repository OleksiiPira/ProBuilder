package com.example.probuilder.presentation.screen.categories.categories_screen

import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.Service

sealed class CategoryScreenEvent {
    // Categories
    data class ShowCategory(val category: Category): CategoryScreenEvent()
    data class CreateCategory(val category: Category): CategoryScreenEvent()
    data object SelectAll: CategoryScreenEvent()
    data class UpdateCategorySelectedState(val category: Category): CategoryScreenEvent()
    data object HideSelectedCategories: CategoryScreenEvent()
    data class FavoriteCategory(val category: Category): CategoryScreenEvent()
    data object FavoriteSelectedCategory: CategoryScreenEvent()

    // Service
    data class HideService(val service: Service): CategoryScreenEvent()
    data class DeleteService(val service: Service): CategoryScreenEvent()

    // Other
    data object Back: CategoryScreenEvent()
}
