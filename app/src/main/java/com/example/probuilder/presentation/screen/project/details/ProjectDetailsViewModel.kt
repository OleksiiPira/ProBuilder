package com.example.probuilder.presentation.screen.project.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.data.remote.ProjectService
import com.example.probuilder.data.remote.RoomService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectDetailsViewModel @Inject constructor(
    private val projectService: ProjectService,
    private val roomService: RoomService,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var project = projectService.getProjectById(savedStateHandle.get<String>("projectId")?: "")
    var rooms = roomService.getRooms(savedStateHandle.get<String>("projectId")?: "")

    fun deleteRoom(projectId: String, roomId: String) = viewModelScope.launch {
        roomService.delete(projectId, roomId)
    }

}
