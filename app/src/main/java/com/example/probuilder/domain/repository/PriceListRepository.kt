package com.example.probuilder.domain.repository

import com.example.probuilder.data.remote.dto.PriceListDto
import com.example.probuilder.domain.model.Job

interface PriceListRepository {
    suspend fun getFeed(): PriceListDto
    suspend fun getJobsByCategoryId(categoryId: String): List<Job>
}