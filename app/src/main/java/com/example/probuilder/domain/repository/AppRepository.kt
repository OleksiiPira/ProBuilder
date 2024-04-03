package com.example.probuilder.domain.repository

import com.example.probuilder.data.remote.dto.CategoryDto
import com.example.probuilder.data.remote.dto.JobDto
import com.example.probuilder.data.remote.dto.RowDto

interface AppRepository {
    suspend fun getRows(): List<RowDto>
    suspend fun getCategories(): Map<String, List<CategoryDto>>
    suspend fun getJobs(): Map<String,List<JobDto>>
}