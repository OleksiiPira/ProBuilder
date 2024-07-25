package com.example.probuilder.data.remote.dto

import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.Service

data class JobDto(
    val id: String,
    val categoryId: String,
    val name: String,
    val price: Int,
    val measure: String
)

fun JobDto.toJob(): Service {
    return Service(
        id = this.id,
        categoryId = this.categoryId,
        name = this.name,
        pricePerUnit = this.price,
        measureUnit = this.measure
    )
}

data class CategoryDto(
    val id: String,
    val parentId: String,
    val name: String,
)

fun CategoryDto.toCategory(): Category {
    return Category(
        id = this.id,
        parentId = this.parentId,
        name = this.name
    )
}
data class RowDto(
    val id: String,
    val name: String,
)
