package com.example.probuilder.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.probuilder.presentation.screen.categories.categories_screen.ItemState
import java.util.UUID

data class Service(
    val id: String = "null",
    val categoryId: String = "null",
    val categoryName: String = "Empty",
    val subCategoryName: String = "Empty",
    val name: String = "Empty",
    val price: Int = 0,
    val measure: String = "null",
    val isHaded: Boolean = false,
    val isFavorite: Boolean = false
)

data class SubCategory(
    val id: String,
    val categoryId: String,
    val categoryName: String = "",
    val name: String = "",
)

@Entity(tableName = "categories")
data class Category(
    val name: String = "",
    val state: ItemState = ItemState.DEFAULT,
    val parentId: String = "",
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
)

data class Feed(
    val feed: List<Category>
)
