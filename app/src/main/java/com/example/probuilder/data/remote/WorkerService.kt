package com.example.probuilder.data.remote

import com.example.probuilder.domain.model.Worker
import kotlinx.coroutines.flow.Flow

interface WorkerService {

    suspend fun save(projectId: String, worker: Worker)

    suspend fun update(projectId: String, worker: Worker)

    suspend fun delete(projectId: String, workerId: String)

    fun getWorkerById(projectId: String, workerId: String): Flow<Worker>

}