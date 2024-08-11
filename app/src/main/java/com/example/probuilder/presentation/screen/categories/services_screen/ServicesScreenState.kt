package com.example.probuilder.presentation.screen.categories.services_screen

import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.Service

data class ServicesScreenState(
    var currCategory: Category = Category(id="main", name = "Categories"),
    var selectedServices: MutableList<Service> = mutableListOf(),
    var isEditMode: Boolean = false
)
