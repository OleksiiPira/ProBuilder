package com.example.probuilder.data.remote

import com.example.probuilder.domain.model.Client
import kotlinx.coroutines.flow.Flow

interface ClientService {

    suspend fun save(projectId: String, client: Client)

    suspend fun update(projectId: String, client: Client)

    suspend fun delete(projectId: String, clientId: String)

    fun getClientByProjectId(projectId: String): Flow<Client>

}