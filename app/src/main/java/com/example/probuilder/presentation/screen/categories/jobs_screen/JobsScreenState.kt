package com.example.probuilder.presentation.screen.categories.jobs_screen

import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.Job

data class JobsScreenState(
    var currCategory: Category = Category(id="main", name = "Categories"),
    var selectedJobs: MutableList<Job> = mutableListOf(),
    var selectedTags: MutableList<String> = mutableListOf(),
    var isEditMode: Boolean = false
)
