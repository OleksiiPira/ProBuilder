package com.example.probuilder.data.local

import com.example.probuilder.data.remote.AppApi
import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.repository.CategoriesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriesRepository @Inject constructor(
    private val dao: CategoriesDao
) {
    suspend fun upsertCategory(category: Category) {
        dao.upsertCategory(category)
    }

    suspend fun deleteCategory(category: Category){
        dao.deleteCategory(category)
    }

    fun getCategories(): Flow<List<Category>>{
        return dao.getCategories()
    }

    fun getCategoryById(id: String): Flow<Category>{
        return dao.getCategoryById(id)
    }

}