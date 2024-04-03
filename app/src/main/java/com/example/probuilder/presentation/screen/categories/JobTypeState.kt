package com.example.probuilder.presentation.screen.categories

data class JobTypeState(
    val isLoading: Boolean = false,
    val priceList: String = "",//PriceList = PriceList(emptyList()),
    val error: String = ""
)
