package com.example.probuilder.data.remote

import com.example.probuilder.domain.model.Project
import kotlinx.coroutines.flow.Flow

interface ProjectService {

    val projects: Flow<List<Project>>

    suspend fun save(project: Project): String

    suspend fun getProject(projectId: String): Project?

    suspend fun delete(projectId: String)

}