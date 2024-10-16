﻿package com.example.probuilder.presentation.screen.project.edit.room

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

    var projectId = savedStateHandle.get<String>("projectId")?: ""

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
            is UpsertSurfaceEvent.Delete -> { _room.value = room.value.copy(surfaces = room.value.surfaces.filter { it.id != event.surface.id }) }
        }
    }

    private fun onSaveSurface() = viewModelScope.launch {
        var newSurface = surface.value.copy()
        when(newSurface.type) {
            SurfaceType.WALL -> newSurface = newSurface.copy(length = 0.0, depth = 0.0)
            SurfaceType.CEILING -> newSurface = newSurface.copy(height = 0.0, depth = 0.0)
            SurfaceType.FLOOR -> newSurface = newSurface.copy(height = 0.0, depth = 0.0)
            SurfaceType.OTHER -> {} // don't clear any field
        }
        _room.value = room.value.copy(surfaces = _room.value.surfaces.toMutableList().apply {
            find { it.id == newSurface.id }.let { outdatedSurface ->
                if (outdatedSurface != null) set(indexOf(outdatedSurface), newSurface)
                else add(newSurface)
            }
            _surface.value = RoomSurface()
        })
    }
}
