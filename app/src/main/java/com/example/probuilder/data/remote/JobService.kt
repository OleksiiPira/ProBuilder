package com.example.probuilder.data.remote

import com.example.probuilder.domain.model.Job
import kotlinx.coroutines.flow.Flow

interface JobService {

    val jobs: Flow<List<Job>>

    suspend fun save(job: Job): Boolean

    suspend fun getJobById(jobId: String): Job?
    suspend fun fetchJobs(categoryId: String, onSuccess: (List<Job>) -> Unit, onFailure: (Exception) -> Unit)
    suspend fun delete(job: Job)

    }