package com.example.probuilder.data.remote

import com.example.probuilder.domain.model.Room
import kotlinx.coroutines.flow.Flow

interface RoomService {

    suspend fun save(projectId: String, room: Room)

    suspend fun update(projectId: String, room: Room)

    suspend fun delete(projectId: String, roomId: String)

    fun getRoomById(projectId: String, roomId: String): Flow<Room>
}
