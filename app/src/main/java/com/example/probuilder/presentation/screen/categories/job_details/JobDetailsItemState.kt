package com.example.probuilder.presentation.screen.categories.job_details

import com.example.probuilder.domain.model.Invoice

data class JobDetailsItemState(
    val id: String = "",
    val invoice: Invoice = Invoice(),
    val name: String = "",
    val quantity: String = "0",
    val unit: String = "м.2",
    val pricePerUnit: String = "0.0",
    val totalPrice: String = "0.0"
)
