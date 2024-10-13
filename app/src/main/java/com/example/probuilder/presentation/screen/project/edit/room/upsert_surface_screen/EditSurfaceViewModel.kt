package com.example.probuilder.presentation.screen.project.edit.room.upsert_surface_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.data.remote.RoomService
import com.example.probuilder.domain.model.Room
import com.example.probuilder.domain.model.RoomSurface
import com.example.probuilder.domain.model.SurfaceType
import com.example.probuilder.presentation.screen.project.edit.room.UpsertSurfaceEvent
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditSurfaceViewModel @Inject constructor(
    private val roomService: RoomService,
    savedStateHandle: SavedStateHandle
    ) : ViewModel() {

    private var projectId = savedStateHandle.get<String>("projectId")?: ""

    private var _room = mutableStateOf(Room())
    val room: State<Room> = _room

    private var _surface = mutableStateOf(RoomSurface())
    val surface: State<RoomSurface> = _surface


    init {
        savedStateHandle.get<String>("room")?.let { roomJson ->
            _room.value = Gson().fromJson(roomJson, Room::class.java)
        }
        savedStateHandle.get<String>("surface")?.let { surfaceJson ->
            _surface.value = Gson().fromJson(surfaceJson, RoomSurface::class.java)
        }
    }

    fun onSurfaceEvent(event: UpsertSurfaceEvent) {
        when (event) {
            is UpsertSurfaceEvent.SetName -> _surface.value = surface.value.copy(name = event.name)
            is UpsertSurfaceEvent.SetType -> _surface.value = surface.value.copy(type = event.type)
            is UpsertSurfaceEvent.SetHeight -> _surface.value = surface.value.copy(height = event.height)
            is UpsertSurfaceEvent.SetWidth -> _surface.value = surface.value.copy(width = event.width)
            is UpsertSurfaceEvent.SetLength -> _surface.value = surface.value.copy(length = event.length)
            is UpsertSurfaceEvent.SetDepth -> _surface.value = surface.value.copy(depth = event.depth)
            is UpsertSurfaceEvent.Save -> saveSurface()
            is UpsertSurfaceEvent.Delete -> {}
        }
    }

    private fun saveSurface(){
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

        viewModelScope.launch {
            val projectId = projectId
            roomService.update(projectId, room.value)
        }
    }
}
