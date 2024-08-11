package com.example.probuilder.presentation.screen.categories.create_service

import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.Service

data class CreateServiceState(
    val pricePerUnit: String = "",
    var isEditMode: Boolean = false
)
