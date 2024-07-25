package com.example.probuilder.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.probuilder.presentation.screen.categories.categories_screen.ItemState
import java.util.UUID

@Entity(tableName = "services")
data class Service(
    val name: String = "",
    val pricePerUnit: Int = 0,
    val measureUnit: String = "",
    val state: ItemState = ItemState.DEFAULT,
    val categoryId: String = "",
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    )

@Entity(tableName = "categories")
data class Category(
    val name: String = "",
    val state: ItemState = ItemState.DEFAULT,
    val parentId: String = "",
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
)

data class Feed(
    val feed: List<Category>
)
