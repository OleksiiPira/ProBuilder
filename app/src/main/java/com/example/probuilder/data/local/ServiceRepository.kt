package com.example.probuilder.data.local

import com.example.probuilder.domain.model.Category
import com.example.probuilder.domain.model.Service
import com.example.probuilder.domain.repository.ServiceDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ServiceRepository @Inject constructor(
    private val dao: ServiceDao
) {
    fun getAllByCategoryId(id: String): Flow<List<Service>> { return dao.getAllByCategoryId(id) }
    suspend fun upsert(service: Service) { dao.upsert(service) }
    suspend fun delete(category: Category) { dao.delete(category) }
}
