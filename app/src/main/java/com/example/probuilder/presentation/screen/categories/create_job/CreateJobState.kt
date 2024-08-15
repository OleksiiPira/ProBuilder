package com.example.probuilder.presentation.screen.categories.create_job

data class CreateJobState(
    val pricePerUnit: String = "",
    var isEditMode: Boolean = false,
    var tags: List<String> = emptyList()
)
