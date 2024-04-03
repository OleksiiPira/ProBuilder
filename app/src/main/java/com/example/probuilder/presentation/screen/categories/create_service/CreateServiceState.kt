package com.example.probuilder.presentation.screen.categories.create_service

import com.example.probuilder.domain.model.Invoice
import java.util.UUID

data class CreateServiceState(
    val name: String = "",
    val category: String = "",
    val subCategory: String = "",
    val unit: String = "м.2",
    val pricePerUnit: String = "0.0",
)
