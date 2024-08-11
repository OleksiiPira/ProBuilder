package com.example.probuilder.data.remote

import com.example.probuilder.domain.model.Service
import kotlinx.coroutines.flow.Flow

interface JobService {

    val jobs: Flow<List<Service>>

    suspend fun save(job: Service): String

    suspend fun getJobById(jobId: String): Service?

    suspend fun delete(job: Service)

    }