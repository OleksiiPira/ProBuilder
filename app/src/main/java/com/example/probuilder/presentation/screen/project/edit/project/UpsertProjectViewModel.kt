package com.example.probuilder.presentation.screen.project.edit.project

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.data.remote.ProjectService
import com.example.probuilder.domain.model.Project
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpsertProjectViewModel @Inject constructor(
    private val projectService: ProjectService,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _project = mutableStateOf(Project())
    val project: State<Project> = _project


    init {
        savedStateHandle.get<String>("project")?.let {
            _project.value = Gson().fromJson(it, Project::class.java)
        }
    }

    fun saveProject() = viewModelScope.launch {
        projectService.save(project.value)
    }

    fun onEvent(event: UpsertProjectEvent) {
        when (event) {
            is UpsertProjectEvent.SetName -> _project.value = project.value.copy(name = event.title)
            is UpsertProjectEvent.SetAddress -> _project.value = project.value.copy(address = event.address)
            is UpsertProjectEvent.SetClientName -> _project.value = project.value.copy(clientName = event.clientName)
            is UpsertProjectEvent.SetEndDate -> _project.value = project.value.copy(endDate = event.endDate)
            is UpsertProjectEvent.SetStartDate -> _project.value = project.value.copy(startDate = event.startDate)
            is UpsertProjectEvent.SetPrice -> _project.value = project.value.copy(price = event.price)
            is UpsertProjectEvent.SetProgress -> _project.value = project.value.copy(progress = event.progress)
        }
    }
}

sealed interface UpsertProjectEvent {
    data class SetName(val title: String): UpsertProjectEvent
    data class SetAddress(val address: String): UpsertProjectEvent
    data class SetClientName(val clientName: String): UpsertProjectEvent
    data class SetPrice(val price: Int): UpsertProjectEvent
    data class SetStartDate(val startDate: String): UpsertProjectEvent
    data class SetEndDate(val endDate: String): UpsertProjectEvent
    data class SetProgress(val progress: Float): UpsertProjectEvent
}
