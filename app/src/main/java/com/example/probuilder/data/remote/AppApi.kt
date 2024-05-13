package com.example.probuilder.data.remote

import com.example.probuilder.data.remote.dto.CategoryDto
import com.example.probuilder.data.remote.dto.JobDto
import com.example.probuilder.data.remote.dto.RowDto
import retrofit2.http.GET

interface AppApi {
    @GET("categories.json")
    suspend fun getCategories(): Map<String, List<CategoryDto>>

    @GET("jobs.json")
    suspend fun getJobs(): Map<String, List<JobDto>>

}
