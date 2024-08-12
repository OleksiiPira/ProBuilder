package com.example.probuilder.presentation.screen.categories.services_screen

import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.Job

data class ServicesScreenState(
    var currCategory: Category = Category(id="main", name = "Categories"),
    var selectedJobs: MutableList<Job> = mutableListOf(),
    var isEditMode: Boolean = false
)
