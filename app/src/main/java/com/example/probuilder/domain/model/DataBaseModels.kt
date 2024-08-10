package com.example.probuilder.domain.model

import com.example.probuilder.presentation.screen.categories.categories.ItemState
import com.google.firebase.firestore.DocumentId
import java.util.UUID

data class Service(
    @DocumentId val id: String = UUID.randomUUID().toString(),
    val categoryId: String = "",
    val userId: String = "",
    val name: String = "",
    val pricePerUnit: Int = 0,
    val measureUnit: String = "",
    val state: ItemState = ItemState.DEFAULT,
    )

//@Entity(tableName = "categories")
data class Category(
    @DocumentId var id: String = "",
    var name: String = "",
    var jobsCount: Int = 0,
    val state: ItemState = ItemState.DEFAULT,
    val parentId: String = "",
    val userId: String = "",
    )

data class Feed(
    val feed: List<Category>
)
