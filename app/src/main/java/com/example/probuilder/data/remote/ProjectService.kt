package com.example.probuilder.data.remote

import com.example.probuilder.domain.model.Project
import com.example.probuilder.domain.model.Client
import kotlinx.coroutines.flow.Flow

interface ProjectService {

    val projects: Flow<List<Project>>

    suspend fun save(project: Project): String

    fun getProjectById(projectId: String): Flow<Project>

    suspend fun delete(projectId: String)

    suspend fun saveClient(projectId: String, client: Client)
    suspend fun updateClient(projectId: String, client: Client)

}