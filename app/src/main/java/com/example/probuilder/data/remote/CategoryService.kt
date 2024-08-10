package com.example.probuilder.data.remote

import com.example.probuilder.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryService {

    val categories: Flow<List<Category>>

    suspend fun save(category: Category): String

    suspend fun getCategory(categoryId: String): Category?

    suspend fun delete(categoryId: String)

    }