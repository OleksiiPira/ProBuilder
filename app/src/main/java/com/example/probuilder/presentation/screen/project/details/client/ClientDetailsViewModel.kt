package com.example.probuilder.presentation.screen.project.details.client

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.probuilder.data.remote.ClientService
import com.example.probuilder.domain.model.Client
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ClientDetailsViewModel @Inject constructor(
    private val projectService: ClientService,
    private val savedStateHandle: SavedStateHandle
    ) : ViewModel() {

    private var _projectId = mutableStateOf(savedStateHandle.get<String>("projectId")?:"")
    val projectId: State<String> = _projectId

    private var _client = projectService.getClientByProjectId(savedStateHandle.get<String>("projectId")?: "")
    val client: Flow<Client> = _client

}
