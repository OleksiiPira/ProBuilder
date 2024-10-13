package com.example.probuilder.presentation.screen.project.details.room

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.data.remote.RoomService
import com.example.probuilder.domain.model.RoomSurface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomDetailsViewModel @Inject constructor(
    private val roomService: RoomService,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val projectId = savedStateHandle.get<String>("projectId")?: ""
    private val roomId = savedStateHandle.get<String>("roomId")?: ""

    val room = roomService.getRoomById(projectId, roomId)

    fun deleteSurfaces(surface: RoomSurface) {
        viewModelScope.launch { roomService.deleteSurface(projectId, roomId, surface) }
    }
}