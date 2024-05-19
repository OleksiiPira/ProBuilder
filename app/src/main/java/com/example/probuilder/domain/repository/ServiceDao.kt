package com.example.probuilder.domain.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.Service
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {
    @Query("SELECT * FROM services WHERE categoryId = :id")
    fun getAllByCategoryId(id: String): Flow<List<Service>>

    @Upsert
    suspend fun upsert(service: Service)

    @Delete
    suspend fun delete(category: Category)
}
