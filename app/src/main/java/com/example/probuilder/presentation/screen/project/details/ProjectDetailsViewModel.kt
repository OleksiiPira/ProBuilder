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
    projectService: ProjectService,
    savedStateHandle: SavedStateHandle,
    private val roomService: RoomService,
) : ViewModel() {
    val projectId = savedStateHandle.get<String>("projectId")?: ""
    var project = projectService.getProjectById(projectId)
    var rooms = roomService.getRooms(projectId)

    fun deleteRoom(projectId: String, roomId: String) = viewModelScope.launch {
        roomService.delete(projectId, roomId)
    }

}
