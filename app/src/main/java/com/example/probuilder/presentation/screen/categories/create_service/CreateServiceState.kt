package com.example.probuilder.presentation.screen.categories.create_service

import com.example.probuilder.domain.model.Category

data class CreateServiceState(
    val currCategory: Category = Category(),
    val name: String = "",
    val categoryName: String = "",
    val unit: String = "м.2",
    val pricePerUnit: String = "0.0",
)
