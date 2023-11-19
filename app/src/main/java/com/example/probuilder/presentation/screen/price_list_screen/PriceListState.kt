package com.example.probuilder.presentation.screen.price_list_screen

import com.example.probuilder.domain.model.PriceList

data class PriceListState(
    val isLoading: Boolean = false,
    val priceList: PriceList = PriceList(emptyList()),
    val error: String = ""
)
