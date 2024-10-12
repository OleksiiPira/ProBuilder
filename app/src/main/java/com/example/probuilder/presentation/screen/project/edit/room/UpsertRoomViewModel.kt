package com.example.probuilder.presentation.screen.project.edit.room

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.data.remote.RoomService
import com.example.probuilder.domain.model.Room
import com.example.probuilder.domain.model.RoomSurface
import com.example.probuilder.domain.model.SurfaceType
import com.example.probuilder.presentation.screen.project.edit.room.UpsertRoomEvent.SetImageUrl
import com.example.probuilder.presentation.screen.project.edit.room.UpsertRoomEvent.SetName
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpsertRoomViewModel @Inject constructor(
    private val roomService: RoomService,
    savedStateHandle: SavedStateHandle
    ) : ViewModel() {

    private var projectId = savedStateHandle.get<String>("projectId")?: ""

    private var _room = mutableStateOf(Room())
    val room: State<Room> = _room

    private var _surface = mutableStateOf(RoomSurface())
    val surface: State<RoomSurface> = _surface


    init {
        savedStateHandle.get<String>("room")?.let { roomStr ->
            _room.value = Gson().fromJson(roomStr, Room::class.java)
        }
    }

    fun roomEvent(event: UpsertRoomEvent) {
        when (event) {
            is SetName -> _room.value = room.value.copy(name = event.name)
            is SetImageUrl -> _room.value = room.value.copy(imageUrl = event.imageUrl)
            is UpsertRoomEvent.Save -> saveRoom()
        }
    }

    private fun saveRoom() = viewModelScope.launch {
        if (room.value.id.isEmpty()) roomService.save(projectId, room.value)
        else roomService.update(projectId, room.value)
    }

    fun surfaceEvent(event: UpsertSurfaceEvent) {
        when (event) {
            is UpsertSurfaceEvent.SetName -> _surface.value = surface.value.copy(name = event.name)
            is UpsertSurfaceEvent.SetType -> _surface.value = surface.value.copy(type = event.type)
            is UpsertSurfaceEvent.SetHeight -> _surface.value = surface.value.copy(height = event.height)
            is UpsertSurfaceEvent.SetWidth -> _surface.value = surface.value.copy(width = event.width)
            is UpsertSurfaceEvent.SetLength -> _surface.value = surface.value.copy(length = event.length)
            is UpsertSurfaceEvent.SetDepth -> _surface.value = surface.value.copy(depth = event.depth)
            is UpsertSurfaceEvent.Save -> onSaveSurface()
        }
    }

    private fun onSaveSurface() = viewModelScope.launch {
        val newSurface = surface.value
        when(newSurface.type) {
            SurfaceType.WALL -> _surface.value = surface.value.copy(length = 0.0, depth = 0.0)
            SurfaceType.CEILING -> _surface.value = surface.value.copy(height = 0.0, depth = 0.0)
            SurfaceType.FLOOR -> _surface.value = surface.value.copy(height = 0.0, depth = 0.0)
            SurfaceType.OTHER -> _surface.value = surface.value // don't clear any field
        }
        _room.value = room.value.copy(surfaces = _room.value.surfaces.toMutableList().apply {
            val outdatedSurface = find { it.id == newSurface.id }
            if (outdatedSurface != null) {
                val outdatedSurfaceIndex = indexOf(outdatedSurface)
                set(outdatedSurfaceIndex, newSurface)
            } else {
                add(newSurface)
            }
        })
    }
}
