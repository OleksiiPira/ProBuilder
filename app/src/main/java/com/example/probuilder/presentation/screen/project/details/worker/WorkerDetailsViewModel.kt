package com.example.probuilder.presentation.screen.project.details.worker

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.probuilder.data.remote.WorkerService
import com.example.probuilder.domain.model.Worker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class WorkerDetailsViewModel @Inject constructor(
    private val projectService: WorkerService,
    private val savedStateHandle: SavedStateHandle
    ) : ViewModel() {

    private var _projectId = mutableStateOf(savedStateHandle.get<String>("projectId")?:"")
    val projectId: State<String> = _projectId

    private var _workerId = mutableStateOf(savedStateHandle.get<String>("workerId")?:"")
    val workerId: State<String> = _workerId

    private var _worker = projectService.getWorkerById(projectId.value, workerId.value)
    val worker: Flow<Worker> = _worker
}
