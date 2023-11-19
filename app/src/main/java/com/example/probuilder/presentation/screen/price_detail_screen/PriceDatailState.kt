package com.example.probuilder.presentation.screen.price_detail_screen

import com.example.probuilder.domain.model.Job
import com.example.probuilder.domain.model.PriceList

data class PriceDatailState(
    val isLoading: Boolean = false,
    val price: Job? = null,
    val error: String = ""
)
