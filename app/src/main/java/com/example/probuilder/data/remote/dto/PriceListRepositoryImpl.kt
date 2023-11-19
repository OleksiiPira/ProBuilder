package com.example.probuilder.data.remote.dto

import com.example.probuilder.data.remote.PriceListApi
import com.example.probuilder.domain.model.Job
import com.example.probuilder.domain.repository.PriceListRepository
import javax.inject.Inject

class PriceListRepositoryImpl @Inject constructor(
    private val api: PriceListApi
) : PriceListRepository {
    override suspend fun getFeed(): PriceListDto {

        return api.getFeed()
    }

    override suspend fun getJobsByCategoryId(categoryId: String): List<Job>  {
        return listOf()
//        return priceList.value?.categories?.find { category -> category.id == categoryId }?.jobs ?: listOf()
    }

}