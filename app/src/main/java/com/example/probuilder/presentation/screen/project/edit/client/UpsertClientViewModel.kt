package com.example.probuilder.presentation.screen.project.edit.client

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.probuilder.data.remote.ClientService
import com.example.probuilder.domain.model.Client
import com.example.probuilder.presentation.screen.project.edit.client.UpsertClientEvent.SetEmail
import com.example.probuilder.presentation.screen.project.edit.client.UpsertClientEvent.SetName
import com.example.probuilder.presentation.screen.project.edit.client.UpsertClientEvent.SetNote
import com.example.probuilder.presentation.screen.project.edit.client.UpsertClientEvent.SetPhone
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpsertClientViewModel @Inject constructor(
    private val clientService: ClientService,
    private val savedStateHandle: SavedStateHandle
    ) : ViewModel() {

    private var _client = mutableStateOf(Client())
    val client: State<Client> = _client

    init {
        savedStateHandle.get<String>("client")?.let { clientStr ->
            _client.value = Gson().fromJson(clientStr, Client::class.java)
        }
    }
    fun saveProject() {
        val projectId = savedStateHandle.get<String>("projectId") ?: ""
        if (projectId.isEmpty()) return

        viewModelScope.launch {
            if (client.value.id.isEmpty()) clientService.save(projectId, client.value)
            else clientService.update(projectId, client.value)
        }
    }

    fun onEvent(event: UpsertClientEvent) {
        when (event) {
            is SetName -> _client.value = client.value.copy(name = event.name)
            is SetPhone -> _client.value = client.value.copy(phone = event.phone)
            is SetEmail -> _client.value = client.value.copy(note = event.email)
            is SetNote -> _client.value = client.value.copy(note = event.note)
        }
    }
}

sealed interface UpsertClientEvent {
    data class SetName(val name: String): UpsertClientEvent
    data class SetEmail(val email: String): UpsertClientEvent
    data class SetPhone(val phone: String): UpsertClientEvent
    data class SetNote(val note: String): UpsertClientEvent
}
