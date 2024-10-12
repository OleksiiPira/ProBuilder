package com.example.probuilder.presentation.screen.project.details.room

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.probuilder.data.remote.RoomService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoomDetailsViewModel @Inject constructor(
    roomService: RoomService,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val projectId = savedStateHandle.get<String>("projectId")?: ""
    private val roomId = savedStateHandle.get<String>("roomId")?: ""

    val room = roomService.getRoomById(projectId, roomId)
}