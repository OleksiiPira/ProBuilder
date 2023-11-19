package com.example.probuilder.data.remote.dto

import com.example.probuilder.domain.model.PriceList

data class PriceListDto(
    val categories: List<CategoryDto>
)

fun PriceListDto.toPriceList(): PriceList {
    return PriceList(
        categories = categories.map { categoryDto -> categoryDto.toCategory() }
    )
}