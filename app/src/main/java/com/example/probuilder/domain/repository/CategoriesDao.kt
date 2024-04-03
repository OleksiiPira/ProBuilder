package com.example.probuilder.domain.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.probuilder.domain.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {
    @Upsert
    suspend fun upsertCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM categories")
    fun getCategories(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE id = :id")//"SELECT * FROM category_items WHERE id = :categoryId")
    fun getCategoryById(id: String): Flow<Category>

    @Query("SELECT * FROM categories WHERE parentId = :id")
    fun getCategoryByParentId(id: String): Flow<List<Category>>

}