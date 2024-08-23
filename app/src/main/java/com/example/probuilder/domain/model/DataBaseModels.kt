package com.example.probuilder.domain.model

import com.example.probuilder.presentation.screen.categories.categories.ItemState
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import java.util.UUID

data class Job(
    @DocumentId val id: String = UUID.randomUUID().toString(),
    val categoryId: String = "",
    val userId: String = "",
    val name: String = "",
    val pricePerUnit: Int = 0,
    val measureUnit: String = "",
    val state: ItemState = ItemState.DEFAULT,
    val tags: List<String> = emptyList()
    )

//@Entity(tableName = "categories")
data class Category(
    @DocumentId var id: String = "",
    var name: String = "",
    @Exclude var jobs: List<Job> = emptyList(),
    var jobsCount: Int = 0,
    val state: ItemState = ItemState.DEFAULT,
    val parentId: String = "",
    val userId: String = "",
    )

data class Project(
    @DocumentId var id: String = "",
    val imageUrl: String = "",
    val name: String = "Назва проекту проекту",
    val address: String = "м. Львів, вул. Карпенка 8а, 39",
    val clientName: String = "Іван Шевченко Андрійович",
    val price: Int = 200000,
    val startDate: String = "20.05.2024",
    val endDate: String = "20.05.2024",
    val progress: Float = 0.2F,
)
