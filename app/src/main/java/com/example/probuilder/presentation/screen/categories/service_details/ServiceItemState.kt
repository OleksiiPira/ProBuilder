package com.example.probuilder.presentation.screen.categories.service_details

import com.example.probuilder.domain.model.Invoice

data class ServiceItemState(
    val id: String = "",
    val invoice: Invoice = Invoice(),
    val name: String = "",
    val quantity: String = "0",
    val unit: String = "м.2",
    val pricePerUnit: String = "0.0",
    val totalPrice: String = "0.0"
)
