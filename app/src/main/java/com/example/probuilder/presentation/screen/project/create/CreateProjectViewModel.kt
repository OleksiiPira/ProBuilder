package com.example.probuilder.presentation.screen.project.create

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
class CreateProjectViewModel @Inject constructor(
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

    fun onEvent(event: CreateProjectEvent) {
        when (event) {
            is CreateProjectEvent.SetName -> _project.value = project.value.copy(name = event.title)
            is CreateProjectEvent.SetAddress -> _project.value = project.value.copy(address = event.address)
            is CreateProjectEvent.SetClientName -> _project.value = project.value.copy(clientName = event.clientName)
            is CreateProjectEvent.SetEndDate -> _project.value = project.value.copy(endDate = event.endDate)
            is CreateProjectEvent.SetStartDate -> _project.value = project.value.copy(startDate = event.startDate)
            is CreateProjectEvent.SetPrice -> _project.value = project.value.copy(price = event.price)
            is CreateProjectEvent.SetProgress -> _project.value = project.value.copy(progress = event.progress)
        }
    }
}
