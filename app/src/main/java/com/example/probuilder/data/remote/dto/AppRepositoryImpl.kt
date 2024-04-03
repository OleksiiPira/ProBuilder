package com.example.probuilder.data.remote.dto

import com.example.probuilder.data.remote.AppApi
import com.example.probuilder.domain.repository.AppRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val api: AppApi
) : AppRepository {
    override suspend fun getRows(): List<RowDto> {
        return api.getRows()["rows"].orEmpty()
    }
    override suspend fun getCategories(): Map<String, List<CategoryDto>> {
        return api.getCategories().orEmpty()
    }
    override suspend fun getJobs(): Map<String, List<JobDto>> {
        return api.getJobs().orEmpty()
    }
}
