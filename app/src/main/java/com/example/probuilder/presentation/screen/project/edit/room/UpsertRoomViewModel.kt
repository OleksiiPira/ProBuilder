package com.example.probuilder.presentation.screen.project.edit.room

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.data.remote.RoomService
import com.example.probuilder.domain.model.Room
import com.example.probuilder.presentation.screen.project.edit.room.UpsertRoomEvent.SetImageUrl
import com.example.probuilder.presentation.screen.project.edit.room.UpsertRoomEvent.SetName
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpsertRoomViewModel @Inject constructor(
    private val roomService: RoomService,
    private val savedStateHandle: SavedStateHandle
    ) : ViewModel() {

    private var _room = mutableStateOf(Room())
    val room: State<Room> = _room

    private var _projectId = mutableStateOf(savedStateHandle.get<String>("projectId")?: "")
    val projectId: State<String> = _projectId

    init {
        savedStateHandle.get<String>("room")?.let { roomStr ->
            _room.value = Gson().fromJson(roomStr, Room::class.java)
        }
    }
    fun saveRoom() = savedStateHandle.get<String>("projectId")?.let { projectId ->
        viewModelScope.launch {
            if (room.value.id.isEmpty()) roomService.save(projectId, room.value)
            else roomService.update(projectId, room.value)
        }
    }

    fun onEvent(event: UpsertRoomEvent) {
        when (event) {
            is SetName -> _room.value = room.value.copy(name = event.name)
            is SetImageUrl -> _room.value = room.value.copy(imageUrl = event.imageUrl)
        }
    }
}

sealed interface UpsertRoomEvent {
    data class SetName(val name: String): UpsertRoomEvent
    data class SetImageUrl(val imageUrl: String): UpsertRoomEvent
}
