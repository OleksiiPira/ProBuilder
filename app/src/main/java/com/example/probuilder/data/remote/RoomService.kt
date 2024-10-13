package com.example.probuilder.data.remote

import com.example.probuilder.domain.model.Room
import com.example.probuilder.domain.model.RoomSurface
import kotlinx.coroutines.flow.Flow

interface RoomService {

    fun getRooms(projectId: String): Flow<List<Room>>

    suspend fun save(projectId: String, room: Room)

    suspend fun update(projectId: String, room: Room)

    suspend fun delete(projectId: String, roomId: String)

    suspend fun deleteSurface(projectId: String, roomId: String, surface: RoomSurface)

    fun getRoomById(projectId: String, roomId: String): Flow<Room>
}
