package com.example.probuilder.presentation.screen.project.edit.worker

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.data.remote.WorkerService
import com.example.probuilder.domain.model.Worker
import com.example.probuilder.presentation.screen.project.edit.worker.UpsertWorkerEvent.SetEmail
import com.example.probuilder.presentation.screen.project.edit.worker.UpsertWorkerEvent.SetName
import com.example.probuilder.presentation.screen.project.edit.worker.UpsertWorkerEvent.SetNote
import com.example.probuilder.presentation.screen.project.edit.worker.UpsertWorkerEvent.SetPhone
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpsertWorkerViewModel @Inject constructor(
    private val workerService: WorkerService,
    private val savedStateHandle: SavedStateHandle
    ) : ViewModel() {

    private var _worker = mutableStateOf(Worker())
    val worker: State<Worker> = _worker

    init {
        savedStateHandle.get<String>("worker")?.let { workerStr ->
            _worker.value = Gson().fromJson(workerStr, Worker::class.java)
        }
    }
    fun saveProject() {
        val projectId = savedStateHandle.get<String>("projectId") ?: ""
        if (projectId.isEmpty()) return

        viewModelScope.launch {
            if (worker.value.id.isEmpty()) workerService.save(projectId, worker.value)
            else workerService.update(projectId, worker.value)
        }
    }

    fun onEvent(event: UpsertWorkerEvent) {
        when (event) {
            is SetName -> _worker.value = worker.value.copy(name = event.name)
            is SetPhone -> _worker.value = worker.value.copy(phone = event.phone)
            is SetEmail -> _worker.value = worker.value.copy(note = event.email)
            is SetNote -> _worker.value = worker.value.copy(note = event.note)
        }
    }
}

sealed interface UpsertWorkerEvent {
    data class SetName(val name: String): UpsertWorkerEvent
    data class SetEmail(val email: String): UpsertWorkerEvent
    data class SetPhone(val phone: String): UpsertWorkerEvent
    data class SetNote(val note: String): UpsertWorkerEvent
}
