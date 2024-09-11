package com.example.probuilder.presentation.screen.project.details.room

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.probuilder.data.remote.RoomService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoomDetailsViewModel @Inject constructor(
    private val roomService: RoomService,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val room = roomService.getRoomById(savedStateHandle.get<String>("projectId")?: "", savedStateHandle.get<String>("roomId")?: "")
}