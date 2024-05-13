package com.example.probuilder.presentation.screen.categories.categories_screen

import com.example.probuilder.domain.model.Category

data class CategoriesScreenState(
    val currCategory: Category = Category(id="main"),
    val isSelectingMode: Boolean = false,
    val itemsMode: ItemState = ItemState.DEFAULT,
    val selectedItems: Map<String, Category> = mapOf(),
    val errorMessage: String = "",
    val hasParent: Boolean = false
)
