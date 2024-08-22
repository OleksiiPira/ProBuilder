package com.example.probuilder.presentation.screen.job.create

data class CreateJobState(
    val pricePerUnit: String = "",
    var isEditMode: Boolean = false,
    var tags: List<String> = emptyList()
)
