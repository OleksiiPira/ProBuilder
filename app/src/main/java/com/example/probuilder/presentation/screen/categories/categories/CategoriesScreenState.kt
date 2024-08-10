package com.example.probuilder.presentation.screen.categories.categories

import com.example.probuilder.domain.model.Category

data class CategoriesScreenState(
    var currCategory: Category = Category(id="main", name = "Categories"),
    var selectedCategories: MutableList<Category> = mutableListOf(),
    val isEditMode: Boolean = false,
)
