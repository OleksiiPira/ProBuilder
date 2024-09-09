package com.example.probuilder.presentation.screen.project.create

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.data.remote.ProjectService
import com.example.probuilder.domain.model.Client
import com.example.probuilder.domain.model.Project
import com.example.probuilder.domain.model.Worker
import com.example.probuilder.presentation.screen.project.edit.client.UpsertClientEvent
import com.example.probuilder.presentation.screen.project.edit.project.UpsertProjectEvent
import com.example.probuilder.presentation.screen.project.edit.worker.UpsertWorkerEvent
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

    private var _client = mutableStateOf(Client())
    val client: State<Client> = _client

    private val _worker = mutableStateOf(Worker())
    val worker: State<Worker> = _worker

    private val _projectStep = mutableStateOf<ProjectStep>(ProjectStep.CreateProject)
    val projectStep: State<ProjectStep> = _projectStep

    fun setProjectStep(projectStep: ProjectStep) {
        _projectStep.value = projectStep
    }

    fun saveProject() = viewModelScope.launch {
        projectService.save(project.value)
    }

    fun projectEvent(event: UpsertProjectEvent) {
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

    fun clientEvent(event: UpsertClientEvent) {
        when (event) {
            is UpsertClientEvent.SetName -> _client.value = client.value.copy(name = event.name)
            is UpsertClientEvent.SetPhone -> _client.value = client.value.copy(phone = event.phone)
            is UpsertClientEvent.SetEmail -> _client.value = client.value.copy(note = event.email)
            is UpsertClientEvent.SetNote -> _client.value = client.value.copy(note = event.note)
        }
    }

    fun workerEvent(event: UpsertWorkerEvent) {
        when (event) {
            is UpsertWorkerEvent.SetName -> _worker.value = worker.value.copy(name = event.name)
            is UpsertWorkerEvent.SetPhone -> _worker.value = worker.value.copy(phone = event.phone)
            is UpsertWorkerEvent.SetEmail -> _worker.value = worker.value.copy(note = event.email)
            is UpsertWorkerEvent.SetNote -> _worker.value = worker.value.copy(note = event.note)
        }
    }
}
