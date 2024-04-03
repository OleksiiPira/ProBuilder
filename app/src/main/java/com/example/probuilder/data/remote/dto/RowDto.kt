package com.example.probuilder.data.remote.dto

import com.example.probuilder.domain.model.Feed
import com.example.probuilder.domain.model.Service
import com.example.probuilder.domain.model.SubCategory
import com.example.probuilder.domain.model.Category

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
        price = this.price,
        measure = this.measure
    )
}

data class CategoryDto(
    val id: String,
    val rowId: String,
    val name: String,
)

fun CategoryDto.toCategory(): SubCategory {
    return SubCategory(
        id = this.id,
        categoryId = this.rowId,
        name = this.name
    )
}

data class FeedDto(
    val feed: List<Category>
)

fun FeedDto.toFeed(): Feed {
    return Feed(
        feed = this.feed
    )
}
data class RowDto(
    val id: String,
    val name: String,
)

fun RowDto.toRow(): Category {
    return Category(
        id = this.id,
        name = this.name,
    )
}
